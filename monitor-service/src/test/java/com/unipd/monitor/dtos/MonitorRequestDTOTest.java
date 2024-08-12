package com.unipd.monitor.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MonitorRequestDTOTest {

    @Test
    public void testMonitorRequestDTOConstructorAndGetters() {
        String query = "query";
        String startDate = "2023-08-01";
        String endDate = "2023-08-31";
        String collectionId = "collectionid";

        MonitorRequestDTO request = new MonitorRequestDTO(query, collectionId, startDate, endDate);

        assertEquals(query, request.getIssueQuery());
        assertEquals(startDate, request.getStartDate());
        assertEquals(endDate, request.getEndDate());
        assertEquals(collectionId, request.getTag());
    }

    @Test
    public void testMonitorRequestDTOSetters() {
        MonitorRequestDTO request = new MonitorRequestDTO();
        request.setIssueQuery("query");
        request.setStartDate("2023-08-01");
        request.setEndDate("2023-08-31");
        request.setTag("collectionid");

        assertEquals("query", request.getIssueQuery());
        assertEquals("2023-08-01", request.getStartDate());
        assertEquals("2023-08-31", request.getEndDate());
        assertEquals("collectionid", request.getTag());
    }
}
