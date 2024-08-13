package com.unipd.monitor.services;

import com.unipd.monitor.dtos.TopicDTO;
import com.unipd.monitor.dtos.TopicResponseDTO;
import com.unipd.monitor.dtos.TopicsRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TopicsService {

    @Value("${mallet.base.url}")
    private String baseUrl;

    private final SearchService searchService;
    private final RestTemplate restTemplate;

    @Autowired
    public TopicsService(SearchService searchService, RestTemplate restTemplate) {
        this.searchService = searchService;
        this.restTemplate = restTemplate;
    }

    public TopicResponseDTO getTopics(TopicsRequestDTO topicsRequest) {
        String collectionId = topicsRequest.getCollectionId();
        String query = topicsRequest.getQuery();
        int numTopics = topicsRequest.getNumOfTopics();
        int numWords = topicsRequest.getNumOfTopWords();

        List<String> articles = searchService.searchArticles(collectionId, query);

        String malletServiceUrl = String.format("%s?numTopics=%d&numWords=%d", baseUrl, numTopics, numWords);

        List<Map<String, Object>> rawTopics;
        try {
            rawTopics = restTemplate.postForObject(malletServiceUrl, articles, List.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to retrieve topics from Mallet service", e);
        }

        List<TopicDTO> topics = new ArrayList<>();
        for (int i = 0; i < rawTopics.size(); i++) {
            Map<String, Object> rawTopic = rawTopics.get(i);
            List<String> topWords = (List<String>) rawTopic.get("words");
            topics.add(new TopicDTO(i, topWords));
        }

        TopicResponseDTO response = new TopicResponseDTO();
        response.setQuery(query);
        response.setTopics(topics);

        return response;
    }
}
