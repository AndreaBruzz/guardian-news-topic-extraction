package com.unipd.monitor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArticleTests {

    @Test
    public void testArticleConstructorAndGetters() {
        String id = "test-id";
        String webTitle = "Test Title";
        String body = "Test content";

        Article article = new Article(id, webTitle, body);

        assertEquals(id, article.getId());
        assertEquals(webTitle, article.getWebTitle());
        assertEquals(body, article.getBody());
    }

    @Test
    public void testArticleSetters() {
        Article article = new Article();
        article.setId("test-id");
        article.setWebTitle("Test Title");
        article.setBody("Test Content");

        assertEquals("test-id", article.getId());
        assertEquals("Test Title", article.getWebTitle());
        assertEquals("Test Content", article.getBody());
    }
}
