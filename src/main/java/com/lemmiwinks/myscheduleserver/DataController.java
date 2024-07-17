package com.lemmiwinks.myscheduleserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private EntityRepository repository;

    @GetMapping
    public List<Entity> getData() {
        return repository.findAll();
    }
}
