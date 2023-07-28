package com.example.springakka.controller;

import com.example.springakka.service.DataStreamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/datastream")
public class DataStreamController {

    private final DataStreamService dataService;

    public DataStreamController(DataStreamService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("/download")
    private String download() throws ExecutionException, InterruptedException {
        return dataService.getData();
    }
}
