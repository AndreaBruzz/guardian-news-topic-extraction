package com.unipd.mallet.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic {
    @JsonProperty("words")
    private ArrayList<String> words;

    public Topic(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }
}
