package com.unipd.monitor.models;

import java.util.List;

public class TopicResponseDTO {
    private String query;
    private List<TopicDTO> topics;

    public TopicResponseDTO() {
    }

    public TopicResponseDTO(String query, List<TopicDTO> topics) {
        this.query = query;
        this.topics = topics;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }
}
