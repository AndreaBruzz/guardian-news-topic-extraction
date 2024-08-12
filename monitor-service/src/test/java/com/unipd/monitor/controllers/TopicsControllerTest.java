package com.unipd.monitor.controllers;

import com.unipd.monitor.dtos.TopicResponseDTO;
import com.unipd.monitor.dtos.TopicsRequestDTO;
import com.unipd.monitor.services.TopicsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TopicsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TopicsService topicsService;

    @InjectMocks
    private TopicsController topicsController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(topicsController).build();
    }

    @Test
    public void testTopicsRetrieval() throws Exception {
        TopicsRequestDTO validRequest = new TopicsRequestDTO("AI", 5, 10, "collection123");
        TopicResponseDTO response = new TopicResponseDTO();

        when(topicsService.getTopics(any(TopicsRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidRequestOnTopicsRetrieval() throws Exception {
        TopicsRequestDTO invalidRequest = new TopicsRequestDTO("", -1, -5, "");

        mockMvc.perform(post("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
