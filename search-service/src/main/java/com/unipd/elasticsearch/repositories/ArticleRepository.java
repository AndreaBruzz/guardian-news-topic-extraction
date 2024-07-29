package com.unipd.elasticsearch.repositories;

import com.unipd.elasticsearch.models.Article;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
    List<Article> findByTitleContaining(String title);
}
