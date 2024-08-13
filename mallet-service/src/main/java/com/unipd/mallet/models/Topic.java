package com.unipd.mallet.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic {
    @JsonProperty("words")
    private List<String> words;

    public Topic(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
