package com.unipd.mallet.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic {
    @JsonProperty("topic")
    private ArrayList<String> topic;

    public Topic(ArrayList<String> topic) {
        this.topic = topic;
    }

    public ArrayList<String> getTopic() {
        return topic;
    }

    public void setTopics(ArrayList<String> topic) {
        this.topic = topic;
    }
}
