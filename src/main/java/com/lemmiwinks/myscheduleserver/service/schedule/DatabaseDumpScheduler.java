package com.lemmiwinks.myscheduleserver.service.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class DatabaseDumpScheduler {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${yandex.object-storage.access-key}")
    private String accessKey;

    @Value("${yandex.object-storage.secret-key}")
    private String secretKey;

    @Value("${yandex.object-storage.bucket-name}")
    private String bucketName;

    @Value("${yandex.object-storage.endpoint}")
    private String endpoint;

    private final String dumpDirectory = "/mnt/dump/"; // Папка для хранения дампов
    private final String dbName = "MyScheduleDb";

    private S3Client s3Client;

    @PostConstruct
    public void initializeS3Client() {
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }

    @Scheduled(cron = "0 0 * * * ?") // Каждый час
    public void createDatabaseDump() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String dumpFile = dumpDirectory + "dump_" + timestamp + ".sql";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mysqldump",
                    "-u", username,
                    "-p" + password,
                    dbName
            );
            processBuilder.redirectOutput(new File(dumpFile));
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Дамп базы данных успешно создан: " + dumpFile);
                uploadToYandexObjectStorage(dumpFile);
            } else {
                System.err.println("Ошибка при создании дампа базы данных.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadToYandexObjectStorage(String dumpFile) {
        File file = new File(dumpFile);

        if (file.exists()) {
            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getName())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
                System.out.println("Файл успешно загружен в Object Storage: " + file.getName());
            } catch (S3Exception e) {
                System.err.println("Ошибка при загрузке файла в Object Storage.");
                e.printStackTrace();
            }
        } else {
            System.err.println("Файл для загрузки не найден: " + dumpFile);
        }
    }
}