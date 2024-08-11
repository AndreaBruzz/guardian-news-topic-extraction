package com.unipd.monitor.models;

import java.util.List;

public class TopicDTO {
    private int id;
    private List<String> topWords;

    public TopicDTO() {
    }

    public TopicDTO(int id, List<String> topWords) {
        this.id = id;
        this.topWords = topWords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getTopWords() {
        return topWords;
    }

    public void setTopWords(List<String> topWords) {
        this.topWords = topWords;
    }
}
