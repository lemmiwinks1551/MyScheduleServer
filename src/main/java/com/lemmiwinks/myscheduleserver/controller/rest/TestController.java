package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.DbVersionsEtag;
import com.lemmiwinks.myscheduleserver.entity.FaqModel;
import com.lemmiwinks.myscheduleserver.repository.DbVersionsEtagRepository;
import com.lemmiwinks.myscheduleserver.repository.FaqRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private DbVersionsEtagRepository dbVersionsEtagRepository;

    private static final Logger logger = LoggerFactory.getLogger(FaqController.class);

    @GetMapping()
    public ResponseEntity<List<FaqModel>> faq(
            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false) String ifNoneMatch) {

        logger.info("Вызван метод faq");

        // Получение текущего ETag из базы данных для таблицы 'faq_model'
        DbVersionsEtag dbVersion = dbVersionsEtagRepository.findByTableName("faq_model");

        // Проверка на null, чтобы избежать ошибок
        if (dbVersion == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        String currentEtag = dbVersion.getEtag();

        // Проверка ETag из заголовка запроса на совпадение с текущим ETag
        if (ifNoneMatch != null && ifNoneMatch.equals(currentEtag)) {
            // Если ETag совпадает, возвращаем статус 304 Not Modified
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        // Если ETag не совпадает или заголовок отсутствует, получаем актуальные данные
        List<FaqModel> faqModels = faqRepository.findAll();

        // Создание заголовков ответа и установка ETag
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ETAG, currentEtag);

        // Возвращение данных с установленным заголовком ETag
        return ResponseEntity.ok().headers(headers).body(faqModels);
    }
}
