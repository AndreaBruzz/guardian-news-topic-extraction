package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;

import static org.junit.jupiter.api.Assertions.*;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IndexingServiceTest {

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @InjectMocks
    private IndexingService indexingService;

    @Mock
    private ElasticsearchIndicesClient indicesClient;

    @Test
    public void testIndexArticle() throws Exception {
        Article article = new Article();
        article.setId("test");
        article.setWebTitle("Test Article");
        article.setBody("This is a test article body");

        indexingService.indexArticle(article, "index");

        boolean exists = indexingService.esClient.indices().exists(c -> c.index("index")).value();
        assertTrue(exists);

        Article indexedArticle = indexingService.esClient.get(g -> g
                .index("index")
                .id("test"), Article.class).source();

        assertNotNull(indexedArticle);
        assertEquals(article.getWebTitle(), indexedArticle.getWebTitle());
        assertEquals(article.getBody(), indexedArticle.getBody());
    }
}
