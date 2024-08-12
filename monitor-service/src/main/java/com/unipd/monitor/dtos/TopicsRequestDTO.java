package com.unipd.monitor.dtos;

import javax.annotation.Nonnegative;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TopicsRequestDTO {
    @NotBlank(message = "Query must be not blank")
    private String query;

    @Nonnegative()
    @NotNull(message = "Number of topics must be not null")
    private int numOfTopics;

    @Nonnegative()
    @NotNull(message = "Number of top words must be not null")
    private int numOfTopWords;

    @NotBlank(message = "Collection ID must be not blank")
    private String collectionId;

    public TopicsRequestDTO() {}

    public TopicsRequestDTO(String query, int numOfTopics, int numOfTopWords, String collectionId) {
        this.query = query;
        this.numOfTopics = numOfTopics;
        this.numOfTopWords = numOfTopWords;
        this.collectionId = collectionId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getNumOfTopics() {
        return numOfTopics;
    }

    public void setNumOfTopics(int numOfTopics) {
        this.numOfTopics = numOfTopics;
    }

    public int getNumOfTopWords() {
        return numOfTopWords;
    }

    public void setNumOfTopWords(int numOfTopWords) {
        this.numOfTopWords = numOfTopWords;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
