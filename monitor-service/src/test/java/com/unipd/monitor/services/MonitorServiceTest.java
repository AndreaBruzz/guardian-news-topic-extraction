package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;  // Import eq
import static org.mockito.Mockito.*;

public class MonitorServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MonitorService monitorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAndSaveApiResponse() throws Exception {
        String mockTag = "tag";
        String mockUrl = "http://mockurl.com";
        String mockResponse = "{\"response\":{\"status\":\"ok\",\"results\":[{\"id\":\"testid\", \"webTitle\":\"This is a web title\", \"body\":\"This is a mockup body\"}]}}";

        when(restTemplate.getForObject(mockUrl, String.class)).thenReturn(mockResponse);

        monitorService.collectArticles(mockUrl, mockTag);

        verify(mongoTemplate, times(1)).save(any(Article.class), eq(mockTag));
    }
}
