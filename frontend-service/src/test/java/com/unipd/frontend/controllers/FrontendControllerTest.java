package com.unipd.frontend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.unipd.frontend.controller.FrontendController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(FrontendController.class)
public class FrontendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"));
    }

    @Test
    public void testMonitorPage() throws Exception {
        this.mockMvc.perform(get("/monitor"))
                .andExpect(status().isOk())
                .andExpect(view().name("monitor.html"));
    }

    @Test
    public void testTopicsPage() throws Exception {
        this.mockMvc.perform(get("/topics?collectionId=TST"))
                .andExpect(status().isOk())
                .andExpect(view().name("topics.html"));
    }
}
