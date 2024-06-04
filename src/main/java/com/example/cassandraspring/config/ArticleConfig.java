package com.example.cassandraspring.config;

import com.example.cassandraspring.models.Article;
import com.example.cassandraspring.repositories.ArticleByRateRepository;
import com.example.cassandraspring.repositories.ArticleByTitleRepository;
import com.example.cassandraspring.repositories.ArticleRepository;
import com.example.cassandraspring.repositories.ArticleRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.mapping.CassandraPersistentEntity;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.MappingCassandraEntityInformation;

import java.util.UUID;

@Configuration
public class ArticleConfig {
    @Bean
    public ArticleRepository articleRepository(
            final CassandraTemplate cassandraTemplate,
            final ArticleByTitleRepository articleByTitleRepository,
            final ArticleByRateRepository articleByRateRepository) {
        final CassandraPersistentEntity<?> entity =
                cassandraTemplate
                        .getConverter()
                        .getMappingContext()
                        .getRequiredPersistentEntity(Article.class);
        final CassandraEntityInformation<Article, UUID> metadata =
                new MappingCassandraEntityInformation<>(
                        (CassandraPersistentEntity<Article>) entity, cassandraTemplate.getConverter());
        return new ArticleRepositoryImpl(
                metadata,
                cassandraTemplate,
                articleByTitleRepository,
                articleByRateRepository);
    }
}
