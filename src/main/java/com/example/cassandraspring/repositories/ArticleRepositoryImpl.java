package com.example.cassandraspring.repositories;

import com.example.cassandraspring.models.*;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleCassandraRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArticleRepositoryImpl extends SimpleCassandraRepository<Article, UUID>
        implements ArticleRepository {
    private final CassandraTemplate cassandraTemplate;
    private final ArticleByTitleRepository articleByTitleRepository;
    private final ArticleByRateRepository articleByRateRepository;

    public ArticleRepositoryImpl(CassandraEntityInformation<Article, UUID> metadata,
                                 CassandraTemplate cassandraTemplate,
                                 ArticleByTitleRepository articleByTitleRepository,
                                 ArticleByRateRepository articleByRateRepository) {
        super(metadata, cassandraTemplate);
        this.cassandraTemplate = cassandraTemplate;
        this.articleByTitleRepository = articleByTitleRepository;
        this.articleByRateRepository = articleByRateRepository;
    }

    @Override
    public <S extends Article> S insert(S article) {
        article.setId(UUID.randomUUID());
        final CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertByTitle(article, batchOps);
        insertByRate(article, batchOps);
        batchOps.insert(article);
        batchOps.execute();
        return article;
    }

    private <S extends Article> void insertByTitle(S article, CassandraBatchOperations batchOps) {
        batchOps.insert(
                new ArticleByTitle(
                        new ArticleByTitleKey(
                                article.getTitle(),
                                article.getId()
                        ),
                        article.getRate()
                )
        );
    }

    private <S extends Article> void insertByRate(S article, CassandraBatchOperations batchOps) {
        batchOps.insert(
                new ArticleByRate(
                        new ArticleByRateKey(
                                article.getRate(),
                                article.getId()
                        ),
                        article.getTitle()
                )
        );
    }

    @Override
    public void delete(Article article) {
        final CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        deleteByTitle(article, batchOps);
        deleteByRate(article, batchOps);
        batchOps.delete(article);
        batchOps.execute();
    }

    @Override
    public void deleteById(UUID id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public Optional<Article> findById(UUID uuid) {
        return Optional.ofNullable(cassandraTemplate.selectOneById(uuid, Article.class));
    }

    private void deleteByTitle(Article article, CassandraBatchOperations batchOps) {
        batchOps.delete(
                articleByTitleRepository.findByKey_TitleAndKey_ArticleId(article.getTitle(), article.getId())
        );
    }

    private void deleteByRate(Article article, CassandraBatchOperations batchOps) {
        batchOps.delete(
                articleByRateRepository.findByKey_RateAndKey_ArticleId(article.getRate(), article.getId())
        );
    }

    @Override
    public <S extends Article> S save(final S movie) {
        return insert(movie);
    }

    @Override
    public <S extends Article> List<S> saveAll(final Iterable<S> movies) {
        return insert(movies);
    }
}
