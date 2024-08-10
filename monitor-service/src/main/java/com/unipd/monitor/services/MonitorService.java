package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ElasticsearchService elasticsearchService;

    public int getPagesNumber(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        int pages = 0;

        String response = restTemplate.getForObject(url, String.class);
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            pages = rootNode.path("response").path("pages").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pages;
    }
    public void collectArticles(String url, String tag) {
        try {
            String response = restTemplate.getForObject(url, String.class);
            List<Article> articles = parseResponse(response);
            for (Article article : articles) {
                System.out.println("Saving into: " + tag);
                mongoTemplate.save(article, tag);
                System.out.println("Saved article: " + article);
                elasticsearchService.index(article, tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Article> parseResponse(String response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();

        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode resultsNode = rootNode.path("response").path("results");

        if (resultsNode.isArray()) {
            for (JsonNode node : resultsNode) {
                Article article = new Article();
                article.setId(node.path("id").asText());
                article.setWebTitle(node.path("webTitle").asText());
                String bodyHtml = node.path("fields").path("body").asText();
                article.setBody(Jsoup.parse(bodyHtml).text());

                articles.add(article);
            }
        }
        return articles;
    }

    public List<String> getCollectionNames() {
        return new ArrayList<>(mongoTemplate.getCollectionNames());
    }
}
