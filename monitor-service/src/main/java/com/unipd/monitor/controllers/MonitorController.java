package com.unipd.monitor.controllers;

import com.unipd.monitor.models.MonitorRequestDTO;
import com.unipd.monitor.services.MonitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MonitorController {

    @Value("${guardian.api.key}")
    private String apiKey;

    @Value("${guardian.base.url}")
    private String baseUrl;

    @Autowired
    private MonitorService monitorService;

    @PostMapping("/collect")
    public ResponseEntity<Map<String, String>> collect(@RequestBody MonitorRequestDTO monitorRequest) {
        try {
            String url = buildApiUrl(monitorRequest.getIssueQuery(), monitorRequest.getStartDate(), monitorRequest.getEndDate());
            int pages = monitorService.getPagesNumber(url);
            for (int i = 1; i <= pages; i++) {
                url += "&page=" + i;
                System.out.println("page: " + i);
                monitorService.collectArticles(url, monitorRequest.getTag());
            }
            return ResponseEntity.ok(Map.of("status", "monitoring"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @GetMapping("/collections")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getCollectionNames() {
        List<String> collectionNames = monitorService.getCollectionNames();
        List<Map<String, String>> data = collectionNames.stream()
                .map(name -> Map.of("collection", name))
                .collect(Collectors.toList());
        Map<String, List<Map<String, String>>> response = Map.of("data", data);
        return ResponseEntity.ok().body(response);
    }

    private String buildApiUrl(String issueQuery, String startDate, String endDate) {
        String url = baseUrl + "?show-fields=body" + "&q=" + issueQuery + "&from-date=" + startDate + "&to-date=" + endDate + "&page-size=200" + "&api-key=" + apiKey;
        System.out.println("Generated URL: " + url);
        return url;
    }
}

