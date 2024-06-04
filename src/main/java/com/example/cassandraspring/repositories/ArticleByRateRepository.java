package com.example.cassandraspring.repositories;

import com.example.cassandraspring.models.ArticleByRate;
import com.example.cassandraspring.models.ArticleByRateKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleByRateRepository extends CassandraRepository<ArticleByRate, ArticleByRateKey> {

    @Query(allowFiltering = false)
    List<ArticleByRate> findByKey_RateAndKey_ArticleId(Double rate, UUID articleId);

    @Query("SELECT article_id AS \"id\", title AS \"title\", rate AS \"rate\" FROM article_by_rate WHERE article_id IN :ids AND rate > :min AND rate < :max")
    List<ArticleByRate> findAllByRate(List<UUID> ids, Double min, Double max);
}
