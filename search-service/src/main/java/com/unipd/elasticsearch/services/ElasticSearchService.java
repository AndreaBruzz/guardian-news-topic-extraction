package com.unipd.elasticsearch.services;

import com.unipd.elasticsearch.models.Article;
import com.unipd.elasticsearch.repositories.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ElasticSearchService {

    @Autowired
    private ArticleRepository articleRepository;

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> searchByTitle(String title) {
        return articleRepository.findByTitleContaining(title);
    }
}

