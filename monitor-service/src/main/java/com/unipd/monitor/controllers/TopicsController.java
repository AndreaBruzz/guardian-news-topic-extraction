package com.unipd.monitor.controllers;

import com.unipd.monitor.dtos.TopicResponseDTO;
import com.unipd.monitor.dtos.TopicsRequestDTO;
import com.unipd.monitor.services.TopicsService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TopicsController {

    private final TopicsService topicsService;

    @Autowired
    public TopicsController(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicResponseDTO> getTopics(@RequestBody TopicsRequestDTO topicsRequest) {
        TopicResponseDTO response = topicsService.getTopics(topicsRequest);
        return ResponseEntity.ok(response);
    }
}
