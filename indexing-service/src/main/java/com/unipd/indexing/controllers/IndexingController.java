package com.unipd.indexing.controllers;

import com.unipd.indexing.services.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IndexingController {

    @Autowired
    private IndexingService articleIndexer;

    @PostMapping("/index-articles")
    public String indexArticles(@RequestParam String indexName) {
        try {
            articleIndexer.indexArticles(indexName);
            return "Indexing completed successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during indexing: " + e.getMessage();
        }
    }
}
