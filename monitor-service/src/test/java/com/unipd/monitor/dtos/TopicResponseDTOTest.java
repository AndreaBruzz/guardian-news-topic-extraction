package com.unipd.monitor.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class TopicResponseDTOTest {

    @Test
    public void testTopicResponseDTOConstructorAndGetters() {
        String query = "example query";
        List<TopicDTO> topics = Arrays.asList(new TopicDTO(1, Arrays.asList("word1", "word2")),
                                              new TopicDTO(2, Arrays.asList("word3", "word4")));

        TopicResponseDTO response = new TopicResponseDTO(query, topics);

        assertEquals(query, response.getQuery());
        assertEquals(topics, response.getTopics());
    }

    @Test
    public void testTopicResponseDTOSetters() {
        TopicResponseDTO response = new TopicResponseDTO();
        response.setQuery("example query");
        List<TopicDTO> topics = Arrays.asList(new TopicDTO(1, Arrays.asList("word1", "word2")),
                                              new TopicDTO(2, Arrays.asList("word3", "word4")));
        response.setTopics(topics);

        assertEquals("example query", response.getQuery());
        assertEquals(topics, response.getTopics());
    }
}
