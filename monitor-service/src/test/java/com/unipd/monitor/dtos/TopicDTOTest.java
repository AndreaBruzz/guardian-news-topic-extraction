package com.unipd.monitor.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class TopicDTOTest {

    @Test
    public void testTopicDTOConstructorAndGetters() {
        int id = 1;
        List<String> topWords = Arrays.asList("word1", "word2", "word3");

        TopicDTO topic = new TopicDTO(id, topWords);

        assertEquals(id, topic.getId());
        assertEquals(topWords, topic.getTopWords());
    }

    @Test
    public void testTopicDTOSetters() {
        TopicDTO topic = new TopicDTO();
        topic.setId(1);
        List<String> topWords = Arrays.asList("word1", "word2", "word3");
        topic.setTopWords(topWords);

        assertEquals(1, topic.getId());
        assertEquals(topWords, topic.getTopWords());
    }
}
