package com.unipd.monitor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TopicsRequestDTOTest {
    @Test
    public void testTopicsRequestDTOConstructorAndGetters() {
        String query = "query";
        int numOfTopics = 10;
        int numOfTopWords = 10;
        String collectionId = "collectionId";


        TopicsRequestDTO request = new TopicsRequestDTO(query, numOfTopics, numOfTopWords, collectionId);

        assertEquals(query, request.getQuery());
        assertEquals(numOfTopics, request.getNumOfTopics());
        assertEquals(numOfTopWords, request.getNumOfTopWords());
        assertEquals(collectionId, request.getCollectionId());
    }

    @Test
    public void testTopicsRequestDTOSetters() {
        TopicsRequestDTO request = new TopicsRequestDTO();
        request.setQuery("query");
        request.setNumOfTopics(10);
        request.setNumOfTopWords(10);
        request.setCollectionId("collectionId");

        assertEquals("query", request.getQuery());
        assertEquals(10, request.getNumOfTopics());
        assertEquals(10, request.getNumOfTopWords());
        assertEquals("collectionId", request.getCollectionId());
    }
}
