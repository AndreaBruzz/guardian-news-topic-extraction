package it.dei.unipd.articlesmonitor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class IssueQueryRequestTest {
    @Test
    public void voidArticleTest() {
        IssueQueryRequest issueQueryRequest = new IssueQueryRequest();

        assertNull(issueQueryRequest.getLabel());
        assertNull(issueQueryRequest.getIssue());
        assertNull(issueQueryRequest.getStartDate());
        assertNull(issueQueryRequest.getEndDate());
    }
}
