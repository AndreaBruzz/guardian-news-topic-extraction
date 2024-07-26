package com.unipd.monitor.controllers;

import com.unipd.monitor.services.MonitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        return ResponseEntity.ok(Map.of("status", "monitoring"));
    }

    @PostMapping("/fetch-articles")
    public ResponseEntity<Map<String, String>> fetchAndSaveApiResponse(@RequestBody Map<String, String> request) {
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
                monitorService.fetchAndSaveApiResponse(url, tag);
            }
            return ResponseEntity.ok(Map.of("status", "monitoring"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    private String buildApiUrl(String issueQuery, String startDate, String endDate) {
        // TODO vedere se c'è una sorta di ENV per queste variabili
        String apiKey = "f4be135e-b40a-4089-a36c-5b6a4cc56b2d";
        String baseUrl = "https://content.guardianapis.com/search";

        String url = baseUrl + "?q=" + issueQuery + "&from-date=" + startDate + "&to-date=" + endDate + "&api-key=" + apiKey;
        System.out.println("Generated URL: " + url); // Log dell'URL generato
        return url;
    }
}

