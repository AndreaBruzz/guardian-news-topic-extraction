package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service("indexing")
public class IndexingService implements AutoCloseable{

    @Value("${elasticsearch.base.url}")
    private String serverUrl;

    private RestClient restClient;
    protected ElasticsearchClient esClient;

    @Override
    public void close() throws Exception {
        if (restClient != null) {
            restClient.close();
        }
    }

    @PostConstruct
    private void init() {
        this.restClient = RestClient.builder(HttpHost.create(serverUrl)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        this.esClient = new ElasticsearchClient(transport);
    }

    public void indexArticle(Article article, String index) {
        try {
            createIndexIfNotExists(index);

            esClient.index(i -> i
                .index(index)
                .id(article.getId())
                .document(article));

            System.out.println("Article indexed: " + article.getWebTitle());
        } catch (IOException e) {
            throw new RuntimeException("Failed to index article: " + article.getId(), e);
        }
    }

    private void createIndexIfNotExists(String indexName) throws IOException {
        boolean exists = esClient.indices().exists(c -> c.index(indexName)).value();
        if (!exists) {
            String mappingJson = """
                    {
                      "mappings": {
                        "properties": {
                          "webTitle": {
                            "type": "text",
                            "analyzer": "english"
                          },
                          "body": {
                            "type": "text",
                            "analyzer": "english"
                          }
                        }
                      }
                    }
                    """;

            ByteArrayInputStream inputStream = new ByteArrayInputStream(mappingJson.getBytes(StandardCharsets.UTF_8));

            CreateIndexRequest createIndexRequest = CreateIndexRequest.of(c -> c
                    .index(indexName)
                    .withJson(inputStream));

            esClient.indices().create(createIndexRequest);
        }
    }
}
