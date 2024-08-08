package com.unipd.monitor.controllers;

import com.unipd.monitor.services.MonitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MonitorController {

    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    private MonitorService monitorService;

    @PostMapping("/collect")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity<Map<String, String>> collect(@RequestBody Map<String, String> request) {
        try {
            String issueQuery = request.get("issueQuery");
            String tag = request.get("tag");
            String startDate = request.get("startDate");
            String endDate = request.get("endDate");

            String url = buildApiUrl(issueQuery, startDate, endDate);
            int pages = monitorService.getPagesNumber(url);
            for (int i = 1; i <= pages; i++) {
                url += "&page=" + i;
                System.out.println("page: " + i);
                monitorService.collectArticles(url, tag);
            }
            return ResponseEntity.ok(Map.of("status", "monitoring"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    private String buildApiUrl(String issueQuery, String startDate, String endDate) {
        String url = baseUrl + "?show-fields=body" + "&q=" + issueQuery + "&from-date=" + startDate + "&to-date=" + endDate + "&page-size=200" + "&api-key=" + apiKey;
        System.out.println("Generated URL: " + url);
        return url;
    }
}

