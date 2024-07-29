package com.unipd.elasticsearch.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "articles")
public class Article {
    @Id
    private String id;
    private String title;
    private String content;
    private String tag;
    private String publicationDate;

    // Getters e Setters
}
