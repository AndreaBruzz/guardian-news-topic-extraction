package com.unipd.indexing.services;

import com.unipd.indexing.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;

import java.io.IOException;
import java.util.List;

@Service
public class IndexingService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticsearchClient esClient;

    public void indexArticles(String indexName) throws IOException {
        List<Article> articles = mongoTemplate.findAll(Article.class);

        BulkRequest.Builder br = new BulkRequest.Builder();
        for (Article article : articles) {
            br.operations(op -> op.index(idx -> idx
                    .index(indexName)
                    .id(article.getId())
                    .document(article)
            ));
        }

        BulkResponse result = esClient.bulk(br.build());
        if (result.errors()) {
            System.err.println("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    System.err.println(item.error().reason());
                }
            }
        }
        System.out.println("HO FINITO DI FARE L'INDEXING");
    }
}
