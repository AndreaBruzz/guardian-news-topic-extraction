package com.unipd.monitor.controllers;

import com.unipd.monitor.models.Article;
import com.unipd.monitor.services.MonitorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class MonitorControllerTest {

    @Mock
    private MonitorService monitorService;

    // @Mock
    // private MongoTemplate mongoTemplate;

    @InjectMocks
    private MonitorController monitorController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(monitorController).build();
    }

    @Test
    public void testArticlesCollect() throws Exception {
        doNothing().when(monitorService).collectArticles(anyString(), anyString());

        String requestJson = "{\"tag\":\"AI\", \"issueQuery\":\"Artificial Intellgence\",\"startDate\":\"2024-01-01\",\"endDate\":\"2024-01-31\"}";

        mockMvc.perform(post("/api/collect")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    // TODO fix this test
    // Sembra ci siano problemi nella connessione a mongo db in 
    // fase di test
    //
    // @Test
    // public void testCollectionRetrieval() throws Exception {
    //     Article article = new Article();
    //     mongoTemplate.save(article, "AI");

    //     mockMvc.perform(get("/api/collections")
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(content().json("{\"data\":[{\"collection\":\"AI\"}]}"));
    // }
}
