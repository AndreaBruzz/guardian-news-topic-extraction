package com.unipd.monitor.controllers;

import com.unipd.monitor.models.TopicsRequestDTO;
import com.unipd.monitor.models.TopicDTO;
import com.unipd.monitor.models.TopicResponseDTO;
import com.unipd.monitor.services.ElasticsearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TopicsController {

    @Value("${mallet.base.url}")
    private String baseUrl;

    private final ElasticsearchService elasticsearchService;
    private final RestTemplate restTemplate;

    @Autowired
    public TopicsController(ElasticsearchService elasticsearchService, RestTemplate restTemplate) {
        this.elasticsearchService = elasticsearchService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicResponseDTO> getTopics(@RequestBody TopicsRequestDTO topicsRequest) {
        String collectionId = topicsRequest.getCollectionId();
        String query = topicsRequest.getQuery();
        int numTopics = topicsRequest.getNumOfTopics();
        int numWords = topicsRequest.getNumOfTopWords();

        ArrayList<String> articles = elasticsearchService.searchArticles(collectionId, query);

        String malletServiceUrl = baseUrl + "?numTopics=" + numTopics + "&numWords=" + numWords;
        List<Map<String, Object>> rawTopics = restTemplate.postForObject(malletServiceUrl, articles, List.class);

        List<TopicDTO> topics = new ArrayList<>();
        for (int i = 0; i < rawTopics.size(); i++) {
            Map<String, Object> rawTopic = rawTopics.get(i);
            List<String> topWords = (List<String>) rawTopic.get("topic");
            topics.add(new TopicDTO(i, topWords));
        }

        TopicResponseDTO response = new TopicResponseDTO();
        response.setQuery(query);
        response.setTopics(topics);

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
