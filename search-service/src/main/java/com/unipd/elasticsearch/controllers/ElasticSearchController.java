package com.unipd.elasticsearch.controllers;

import com.unipd.elasticsearch.models.Article;
import com.unipd.elasticsearch.services.ElasticSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ElasticSearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

}
