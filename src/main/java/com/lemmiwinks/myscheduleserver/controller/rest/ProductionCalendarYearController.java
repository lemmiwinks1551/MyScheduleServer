package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.DbVersionsEtag;
import com.lemmiwinks.myscheduleserver.entity.ProductionCalendarModel;
import com.lemmiwinks.myscheduleserver.repository.DbVersionsEtagRepository;
import com.lemmiwinks.myscheduleserver.repository.ProductionCalendarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendar")
public class ProductionCalendarYearController {

    @Autowired
    private ProductionCalendarRepository productionCalendarRepository;

    @Autowired
    private DbVersionsEtagRepository dbVersionsEtagRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductionCalendarYearController.class);

    @GetMapping("/get-year/{year}")
    public ResponseEntity<List<ProductionCalendarModel>> getYearDates(
            @PathVariable String year,
            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false) String ifNoneMatch) {

        String tableName = "production_calendar_model";
        logger.info("Вызван метод getYearDates");

        // Получение текущего ETag из базы данных для таблицы '
        DbVersionsEtag dbVersion = dbVersionsEtagRepository.findByTableName(tableName);

        // Проверка на null, чтобы избежать ошибок
        if (dbVersion == null) {
            logger.info(String.format("dbVersion не найден в таблице %s", tableName));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        String currentEtag = dbVersion.getEtag();

        // Проверка ETag из заголовка запроса на совпадение с текущим ETag
        if (ifNoneMatch != null && ifNoneMatch.equals(currentEtag)) {
            // Если ETag совпадает, возвращаем статус 304 Not Modified
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        // Если ETag не совпадает или заголовок отсутствует, получаем актуальные данные
        List<ProductionCalendarModel> allRecords = productionCalendarRepository.findAll();

        // Фильтрация записей, содержащих указанный год в поле date
        List<ProductionCalendarModel> filteredRecords = allRecords.stream()
                .filter(record -> record.getDate().contains(year))
                .collect(Collectors.toList());

        // Создание заголовков ответа и установка ETag
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ETAG, currentEtag);

        // Возвращение данных с установленным заголовком ETag
        return ResponseEntity.ok().headers(headers).body(filteredRecords);
    }
}