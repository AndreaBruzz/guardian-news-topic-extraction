package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service("search")
public class SearchService implements AutoCloseable{

    @Value("${elasticsearch.base.url}")
    private String serverUrl;

    private RestClient restClient;
    private ElasticsearchClient esClient;

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

    @Autowired
    public SearchService(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    public List<String> searchArticles(String index, String query) {
        try {
            SearchResponse<Article> initialSearchResponse = esClient.search(s -> s
                    .index(index)
                    .query(q -> q
                            .multiMatch(m -> m
                                    .query(query)
                                    .fields("webTitle^1", "body^3")
                            )
                    )
                    .size(1),
                    Article.class
            );

            double maxScore = initialSearchResponse.hits().maxScore();
            double minScore = maxScore * 0.7;

            SearchResponse<Article> finalSearchResponse = esClient.search(s -> s
                    .index(index)
                    .query(q -> q
                            .multiMatch(m -> m
                                    .query(query)
                                    .fields("webTitle^1", "body^3")
                            )
                    )
                    .minScore(minScore),
                    Article.class
            );
        return finalSearchResponse.hits().hits().stream()
                .map(hit -> hit.source().getBody())
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to search articles", e);
        }
    }
}
