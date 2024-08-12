package com.unipd.mallet.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TopicTest {

    @Test
    public void testTopicInitialization() {
        ArrayList<String> words = new ArrayList<>(Arrays.asList("machine", "learning", "AI"));
        Topic topic = new Topic(words);

        assertNotNull(topic);
        assertEquals(words, topic.getWords());
    }

    @Test
    public void testGetWords() {
        ArrayList<String> words = new ArrayList<>(Arrays.asList("data", "science", "big", "data"));
        Topic topic = new Topic(words);

        ArrayList<String> retrievedWords = topic.getWords();
        assertNotNull(retrievedWords);
        assertEquals(4, retrievedWords.size());
        assertEquals("data", retrievedWords.get(0));
        assertEquals("science", retrievedWords.get(1));
    }

    @Test
    public void testSetWords() {
        ArrayList<String> initialWords = new ArrayList<>(Arrays.asList("neural", "network"));
        Topic topic = new Topic(initialWords);

        ArrayList<String> newWords = new ArrayList<>(Arrays.asList("deep", "learning"));
        topic.setWords(newWords);

        ArrayList<String> updatedWords = topic.getWords();
        assertNotNull(updatedWords);
        assertEquals(2, updatedWords.size());
        assertEquals("deep", updatedWords.get(0));
        assertEquals("learning", updatedWords.get(1));
    }
}
