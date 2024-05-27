package it.dei.unipd.articlesmonitor.controller;

import it.dei.unipd.articlesmonitor.model.IssueQueryRequest;
import it.dei.unipd.articlesmonitor.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadJson(@RequestBody IssueQueryRequest issueQuery) {
        String fileName = issueQueryToFilePath(issueQuery.getIssue(), issueQuery.getLabel());
        try {
            articleService.saveArticlesFromJson(fileName, issueQuery.getStartDate(), issueQuery.getEndDate());
            return new ResponseEntity<>("MONITORING\n   ", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR\n", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String issueQueryToFilePath(String issue, String label) {
        issue = issue.strip();
        issue = issue.replace(' ', '_');
        issue = issue.toLowerCase();

        label = label.strip();
        label = label.replace(' ', '_');
        label = label.toLowerCase();

        return String.format("%s_%s", issue, label);
    }
}

