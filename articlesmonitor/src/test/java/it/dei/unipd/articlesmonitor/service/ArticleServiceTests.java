package it.dei.unipd.articlesmonitor.service;

import it.dei.unipd.articlesmonitor.model.Article;
import it.dei.unipd.articlesmonitor.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class ArticleServiceTests {

    private ArticleService articleService;
    private ArticleRepository articleRepository;

    @BeforeEach
    public void setUp() {
        articleRepository = Mockito.mock(ArticleRepository.class);
        articleService = new ArticleService();
        articleService.articleRepository = articleRepository;
    }

    @Test
    public void testSaveArticlesFromJsonFilteringByDate() throws IOException {
        String filename = "artificial_intelligence_test";
        String startDate = "1984-01-01";
        String endDate = "2024-03-31";

        articleService.saveArticlesFromJson(filename, startDate, endDate);

        ArgumentCaptor<List<Article>> captor = ArgumentCaptor.forClass(List.class);
        verify(articleRepository).saveAll(captor.capture());

        List<Article> savedArticles = captor.getValue();
        assertEquals(1, savedArticles.size());
    }
}
