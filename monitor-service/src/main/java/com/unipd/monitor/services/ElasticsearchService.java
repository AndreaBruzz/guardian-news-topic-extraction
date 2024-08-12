package com.unipd.monitor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.unipd.monitor.models.Article;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("elasticsearch")
public class ElasticsearchService {

    @Value("${elasticsearch.base.url}")
    private String serverUrl;

    private RestClient restClient;
    private ElasticsearchClient esClient;

    public ElasticsearchService() {
    }

    @PostConstruct
    private void init() {
        this.restClient = RestClient.builder(HttpHost.create(serverUrl)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        this.esClient = new ElasticsearchClient(transport);
    }

    public void closeConnection() {
        try {
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void index(Article article, String index) {
        try {
            esClient.index(i -> i
                .index(index)
                .id(article.getId())
                .document(article));

            System.out.println("Article indexed: " + article.getWebTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> searchArticles(String index, String query) {
        ArrayList<String> articles = new ArrayList<>();

        try {
            List<Hit<Article>> hits = searchByQuery(index, query);
            for (Hit<Article> hit : hits) {
                Article article = hit.source();
                System.out.println("Found article: " + article.getWebTitle());
                articles.add(article.getBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    private List<Hit<Article>> searchByQuery(String index, String query) throws IOException {
        SearchResponse<Article> response = esClient.search(s -> s
                .index(index)
                .query(q -> q.multiMatch(t -> t
                        .query(query)
                        .fields("webTitle", "body"))),
 Article.class);

        return response.hits().hits();
    }
}
