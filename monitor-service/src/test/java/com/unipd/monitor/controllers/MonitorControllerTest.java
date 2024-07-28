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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        mockMvc.perform(post("/collect")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }
}
