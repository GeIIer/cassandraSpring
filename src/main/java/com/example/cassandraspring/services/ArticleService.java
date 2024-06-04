package com.example.cassandraspring.services;

import com.example.cassandraspring.models.Article;
import com.example.cassandraspring.models.ArticleByRate;
import com.example.cassandraspring.models.ArticleByTitle;
import com.example.cassandraspring.repositories.ArticleByRateRepository;
import com.example.cassandraspring.repositories.ArticleByTitleRepository;
import com.example.cassandraspring.repositories.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService implements BaseService<Article> {
    private final ArticleRepository articleRepository;
    private final ArticleByTitleRepository articleByTitleRepository;
    private final ArticleByRateRepository articleByRateRepository;

    public ArticleService(ArticleRepository articleRepository, ArticleByTitleRepository articleByTitleRepository, ArticleByRateRepository articleByRateRepository) {
        this.articleRepository = articleRepository;
        this.articleByTitleRepository = articleByTitleRepository;
        this.articleByRateRepository = articleByRateRepository;
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article getById(UUID id) {
        return articleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public Article create(Article table) {
        return articleRepository.insert(table);
    }

    @Override
    public Article update(Article table) {
        if (!articleRepository.existsById(table.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return articleRepository.save(table);
    }

    @Override
    public void deleteById(UUID id) {
        if (!articleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        articleRepository.deleteById(id);
    }

    public List<ArticleByTitle> getByTitle(String title) {
        return articleByTitleRepository.findAllByKey_Title(title);
    }

    public List<ArticleByRate> getByRate(Double min, Double max) {
        List<UUID> ids = articleRepository.findAll()
                .stream()
                .map(Article::getId)
                .toList();
        return articleByRateRepository.findAllByRate(ids, min, max);
    }
}
