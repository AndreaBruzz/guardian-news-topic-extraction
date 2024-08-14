package com.unipd.monitor.controllers;

import com.unipd.monitor.services.MonitorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

public class MonitorControllerTest {

    @Mock
    private MonitorService monitorService;

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

    @Test
    public void testInvalidRequestOnArticlesCollect() throws Exception {
        String invalidRequestJson = "{ \"issueQuery\": \"\", \"startDate\": null, \"endDate\": null, \"tag\": \"\" }";

        mockMvc.perform(post("/api/collect")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson))
                .andExpect(status().isBadRequest());
    }

      @Test
    public void testGetCollections() throws Exception {
        List<String> collections = Arrays.asList("collection1", "collection2");
        when(monitorService.getCollectionNames()).thenReturn(collections);

        mockMvc.perform(get("/api/collections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].collection").value("collection1"))
                .andExpect(jsonPath("$.data[1].collection").value("collection2"));
    }

    @Test
    public void testDeleteCollection() throws Exception {
        String collectionId = "testCollection";
        when(monitorService.getCollectionNames()).thenReturn(Arrays.asList("testCollection"));

        mockMvc.perform(delete("/api/collections/{collectionId}", collectionId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteInexistingCollection() throws Exception {
        String collectionId = "nonExistentCollection";
        when(monitorService.getCollectionNames()).thenReturn(Arrays.asList("testCollection"));

        mockMvc.perform(delete("/api/collections/{collectionId}", collectionId))
                .andExpect(status().isNotFound());
    }
}
