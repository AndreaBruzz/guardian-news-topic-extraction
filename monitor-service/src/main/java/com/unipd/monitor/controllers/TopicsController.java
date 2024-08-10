package com.unipd.monitor.controllers;

import com.unipd.monitor.models.TopicsRequestDTO;
import com.unipd.monitor.services.ElasticsearchService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TopicsController {

    private final ElasticsearchService elasticsearchService;

    @Autowired
    public TopicsController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @PostMapping("/topics")
    public ResponseEntity<?> getTopics(@RequestBody TopicsRequestDTO topicsRequest) {

        return ResponseEntity.ok("OK");
    }
}
