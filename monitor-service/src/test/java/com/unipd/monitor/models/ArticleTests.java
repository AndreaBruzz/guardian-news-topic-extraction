package com.unipd.monitor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArticleTests {

    @Test
    public void testArticleConstructorAndGetters() {
        String id = "test-id";
        String type = "article";
        String sectionId = "test-section";
        String sectionName = "Test Section";
        String webPublicationDate = "2024-04-08T06:00:26Z";
        String webTitle = "Test Title";
        String webUrl = "http://example.com";
        String apiUrl = "http://example.com/api";
        boolean isHosted = false;
        String pillarId = "pillar/test";
        String pillarName = "Test Pillar";

        Article article = new Article(id, type, sectionId, sectionName, webPublicationDate, webTitle, webUrl, apiUrl, isHosted, pillarId, pillarName);

        assertEquals(id, article.getId());
        assertEquals(type, article.getType());
        assertEquals(sectionId, article.getSectionId());
        assertEquals(sectionName, article.getSectionName());
        assertEquals(webPublicationDate, article.getWebPublicationDate());
        assertEquals(webTitle, article.getWebTitle());
        assertEquals(webUrl, article.getWebUrl());
        assertEquals(apiUrl, article.getApiUrl());
        assertEquals(isHosted, article.isHosted());
        assertEquals(pillarId, article.getPillarId());
        assertEquals(pillarName, article.getPillarName());
    }

    @Test
    public void testArticleSetters() {
        Article article = new Article();
        article.setId("test-id");
        article.setType("article");
        article.setSectionId("test-section");
        article.setSectionName("Test Section");
        article.setWebPublicationDate("2024-04-08T06:00:26Z");
        article.setWebTitle("Test Title");
        article.setWebUrl("http://example.com");
        article.setApiUrl("http://example.com/api");
        article.setHosted(false);
        article.setPillarId("pillar/test");
        article.setPillarName("Test Pillar");

        assertEquals("test-id", article.getId());
        assertEquals("article", article.getType());
        assertEquals("test-section", article.getSectionId());
        assertEquals("Test Section", article.getSectionName());
        assertEquals("2024-04-08T06:00:26Z", article.getWebPublicationDate());
        assertEquals("Test Title", article.getWebTitle());
        assertEquals("http://example.com", article.getWebUrl());
        assertEquals("http://example.com/api", article.getApiUrl());
        assertFalse(article.isHosted());
        assertEquals("pillar/test", article.getPillarId());
        assertEquals("Test Pillar", article.getPillarName());
    }
}
