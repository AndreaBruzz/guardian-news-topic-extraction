package it.dei.unipd.articlesmonitor.repository;

import it.dei.unipd.articlesmonitor.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
}
