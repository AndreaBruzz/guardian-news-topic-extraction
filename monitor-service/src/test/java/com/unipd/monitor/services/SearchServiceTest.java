package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @InjectMocks
    private IndexingService indexingService;

    @InjectMocks
    private SearchService searchService;

    @Mock
    private ElasticsearchIndicesClient indicesClient;

    @Test
    public void testIndexArticle() throws Exception {
        Article article = new Article();
        article.setId("test");
        article.setWebTitle("Test Article");
        article.setBody("This is a test article body");

        indexingService.indexArticle(article, "index");

        List<String> results = searchService.searchArticles("index", "test");

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(article.getBody(), results.get(0));
    }
}
