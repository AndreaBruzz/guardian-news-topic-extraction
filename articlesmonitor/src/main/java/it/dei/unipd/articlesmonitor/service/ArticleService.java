package it.dei.unipd.articlesmonitor.service;

import it.dei.unipd.articlesmonitor.model.Article;
import it.dei.unipd.articlesmonitor.repository.ArticleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ArticleService {

    @Autowired ArticleRepository articleRepository;

    public void saveArticlesFromJson(String filename, String startDate, String endDate) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String resourcePath = String.format("storage/%s.json", filename);
        Resource resource = new ClassPathResource(resourcePath);

        if (!resource.exists()) {
            System.out.println("File not found: " + resourcePath);
            throw new IOException();
        }

        JsonNode rootNode = mapper.readTree(resource.getInputStream());
        JsonNode resultsNode = rootNode.path("response").path("results");
        List<Article> articles = new ArrayList<>();

        if (rootNode.path("response").path("status").asText().equals("ok")) {
            Iterator<JsonNode> elements = resultsNode.elements();
            DateTimeFormatter publicationDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
            DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate start = LocalDate.parse(startDate, simpleDateFormatter);
            LocalDate end = LocalDate.parse(endDate, simpleDateFormatter);

            while (elements.hasNext()) {
                JsonNode articleNode = elements.next();
                try {
                    Article article = mapper.treeToValue(articleNode, Article.class);
                    OffsetDateTime publicationDateTime = OffsetDateTime.parse(article.getWebPublicationDate(), publicationDateFormatter);
                    LocalDate publicationDate = publicationDateTime.toLocalDate();

                    if ((publicationDate.isEqual(start) || publicationDate.isAfter(start)) &&
                        (publicationDate.isEqual(end) || publicationDate.isBefore(end))) {
                        articles.add(article);
                    }
                } catch (IOException e) {
                    System.out.println("Failed to parse article node");
                    throw new IOException();
                }
            }
        } else {
            System.out.println("Response status not OK");
        }

        try {
            articleRepository.saveAll(articles);
            System.out.println("Articles saved successfully");
        } catch (Exception e) {
            System.out.println("Failed to save articles");
            throw new IOException();
        }
    }
}
