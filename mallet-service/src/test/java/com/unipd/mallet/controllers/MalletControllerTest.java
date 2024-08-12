package com.unipd.mallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unipd.mallet.services.MalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MalletController.class)
public class MalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MalletService malletService;

    @Test
    public void testGetTopics() throws Exception {
        List<String> documents = Arrays.asList("Artificial Intelligence and Machine Learning", "Deep Learning in Neural Networks");
        String jsonDocuments = objectMapper.writeValueAsString(documents);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/mallet/topics")
                .content(jsonDocuments)
                .contentType(MediaType.APPLICATION_JSON)
                .param("numTopics", "2")
                .param("numWords", "3"))
                .andExpect(status().isOk());

        Mockito.verify(malletService).extractTopics(any(List.class), eq(2), eq(3));
    }
}
