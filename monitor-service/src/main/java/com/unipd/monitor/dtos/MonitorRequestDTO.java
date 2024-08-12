package com.unipd.monitor.dtos;

public class MonitorRequestDTO {
    private String issueQuery;
    private String tag;
    private String startDate;
    private String endDate;

    public MonitorRequestDTO() {}

    public MonitorRequestDTO(String issueQuery, String tag, String startDate, String endDate) {
        this.issueQuery = issueQuery;
        this.tag = tag.toLowerCase();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getIssueQuery() {
        return issueQuery;
    }

    public void setIssueQuery(String issueQuery) {
        this.issueQuery = issueQuery;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag.toLowerCase();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
