package it.dei.unipd.articlesmonitor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArticleTest {
    @Test
    public void voidArticleTest() {
        Article article = new Article();

        assertNull(article.getId());
        assertNull(article.getType());
        assertNull(article.getSectionId());
        assertNull(article.getSectionName());
        assertNull(article.getWebPublicationDate());
        assertNull(article.getWebTitle());
        assertNull(article.getWebUrl());
        assertNull(article.getApiUrl());
        assertFalse(article.isHosted());
        assertNull(article.getPillarId());
        assertNull(article.getPillarName());
        assertNull(article.getTag());
    }
}
