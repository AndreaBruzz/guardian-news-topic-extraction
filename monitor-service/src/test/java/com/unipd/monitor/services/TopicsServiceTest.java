package com.unipd.monitor.services;

import com.unipd.monitor.dtos.TopicResponseDTO;
import com.unipd.monitor.dtos.TopicsRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TopicsServiceTest {

    @Mock
    private SearchService searchService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TopicsService topicsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchAndTopicModeling() {
        TopicsRequestDTO requestDTO = new TopicsRequestDTO("AI", 2, 5, "collection123");
        List<String> articles = new ArrayList<>();
        articles.add("Article 1");
        articles.add("Article 2");

        List<Map<String, Object>> rawTopics = new ArrayList<>();
        Map<String, Object> topic1 = new HashMap<>();
        topic1.put("topic", List.of("word1", "word2"));
        rawTopics.add(topic1);

        when(searchService.searchArticles("collection123", "AI")).thenReturn((ArrayList<String>) articles);
        when(restTemplate.postForObject(anyString(), eq(articles), eq(List.class))).thenReturn(rawTopics);

        TopicResponseDTO response = topicsService.getTopics(requestDTO);

        assertEquals(1, response.getTopics().size());
        assertEquals("AI", response.getQuery());
        assertEquals(List.of("word1", "word2"), response.getTopics().get(0).getTopWords());
    }
}
