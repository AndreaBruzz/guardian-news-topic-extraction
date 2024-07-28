package com.unipd.monitor.services;

import com.unipd.monitor.models.Article;
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
    public void fetchAndSaveApiResponse(String url, String tag) {
        try {
            String response = restTemplate.getForObject(url, String.class);
            List<Article> articles = parseResponse(response, tag);
            for (Article article : articles) {
                mongoTemplate.save(article, "articles"); // Nome della collezione specificato qui
                System.out.println("Saved article: " + article); // Log aggiunto
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Article> parseResponse(String response, String tag) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();

        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode resultsNode = rootNode.path("response").path("results");

        if (resultsNode.isArray()) {
            for (JsonNode node : resultsNode) {
                Article article = new Article();
                article.setTag(tag);
                article.setId(node.path("id").asText());
                article.setType(node.path("type").asText());
                article.setSectionId(node.path("sectionId").asText());
                article.setSectionName(node.path("sectionName").asText());
                article.setWebPublicationDate(node.path("webPublicationDate").asText());
                article.setWebTitle(node.path("webTitle").asText());
                article.setWebUrl(node.path("webUrl").asText());
                article.setApiUrl(node.path("apiUrl").asText());
                article.setHosted(node.path("isHosted").asBoolean());
                article.setPillarId(node.path("pillarId").asText());
                article.setPillarName(node.path("pillarName").asText());
                article.setBody(node.path("fields").path("body").asText());

                articles.add(article);
            }
        }
        return articles;
    }
}
