package com.unipd.mallet.services;

import com.unipd.mallet.models.Topic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MalletServiceTest {

    private MalletService malletService;

    @BeforeEach
    public void setUp() {
        malletService = new MalletService();
    }

    @Test
    public void testExtractTopics() throws Exception {
        List<String> articles = Arrays.asList(
            "This is a test article about machine learning.",
            "This is another article about deep learning and AI.",
            "Natural language processing is a field of AI."
        );

        int numTopics = 2;
        int numWords = 3;

        List<Topic> topics = malletService.extractTopics(articles, numTopics, numWords);

        assertNotNull(topics);
        assertEquals(numTopics, topics.size());

        for (Topic topic : topics) {
            assertNotNull(topic);
            assertEquals(numWords, topic.getWords().size());
        }
    }
}
