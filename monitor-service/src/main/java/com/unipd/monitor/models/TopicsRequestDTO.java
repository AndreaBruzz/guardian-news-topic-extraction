package com.unipd.monitor.models;

public class TopicsRequestDTO {
    private String query;
    private int numOfTopics;
    private int numOfTopWords;
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
