package com.unipd.monitor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Article {

    @Id
    private String id;
    private String webTitle;
    private String body;

    // Default constructor
    public Article() {}

    // Overloaded constructor
    public Article(String id, String webTitle, String body) {
        this.id = id;
        this.webTitle = webTitle;
        this.body = body;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
