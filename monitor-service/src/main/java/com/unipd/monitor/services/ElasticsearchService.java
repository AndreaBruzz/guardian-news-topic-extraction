package com.unipd.monitor.services;

import org.springframework.stereotype.Service;

import com.unipd.monitor.models.Article;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("elastic")
public class ElasticsearchService {

    private final RestClient restClient;
    private final ElasticsearchClient esClient;

    public ElasticsearchService() {
        String serverUrl = System.getenv("ELASTICSEARCH_URL");

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

    private List<Hit<Article>> search(String index, String field, String query) throws IOException {
        SearchResponse<Article> response = esClient.search(s -> s
                .index(index)
                .query(q -> q.match(t -> t.field(field).query(query))),
                Article.class);

        System.out.println("Searching done");
        return response.hits().hits();
    }

    public ArrayList<String> retrieveDocuments(String index, String field, String query) {
        ArrayList<String> articleContents = new ArrayList<>();
        try {
            List<Hit<Article>> hits = search(index, field, query);
            if (hits.isEmpty()) {
                System.out.println("No articles found");
            } else {
                for (Hit<Article> hit : hits) {
                    Article article = hit.source();
                    System.out.println("Found article: " + article.getWebTitle());
                    articleContents.add(article.getBody());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleContents;
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
}
