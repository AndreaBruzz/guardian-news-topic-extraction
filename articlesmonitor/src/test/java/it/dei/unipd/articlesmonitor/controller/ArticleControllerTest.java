package it.dei.unipd.articlesmonitor.controller;

import it.dei.unipd.articlesmonitor.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    public void testSuccessfullUploadJson() throws Exception {
        String validRequestBody = "{ \"label\": \"AI\", \"issue\": \"artificial intelligence\", \"startDate\": \"1984-01-01\", \"endDate\": \"2024-03-31\" }";

        Mockito.doNothing().when(articleService).saveArticlesFromJson(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        mockMvc.perform(post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("MONITORING"));
    }

    @Test
    public void testFailUploadJson() throws Exception {
        String validRequestBody = "{ \"label\": \"failure\", \"issue\": \"file not found\", \"startDate\": \"1984-01-01\", \"endDate\": \"2024-03-31\" }";

        Mockito.doThrow(new IOException("File not found")).when(articleService).saveArticlesFromJson(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        mockMvc.perform(post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("ERROR"));
    }

    @Test
    public void testInvalidJson() throws Exception {
        String invalidRequestBody = "{ \"label\": \"AI\", \"issue\": \"artificial intelligence\", \"startDate\": \"1984-01-01\", \"endDate\": \"2024-03-31\" "; // Malformed JSON

        mockMvc.perform(post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
}
