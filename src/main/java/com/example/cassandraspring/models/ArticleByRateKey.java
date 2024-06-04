package com.example.cassandraspring.models;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Data
@PrimaryKeyClass
public class ArticleByRateKey {
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Double rate;

    @PrimaryKeyColumn(name = "article_id", ordinal = 1, ordering = Ordering.DESCENDING, type = PrimaryKeyType.PARTITIONED)
    private UUID articleId;

    public ArticleByRateKey(Double rate, UUID articleId) {
        this.rate = rate;
        this.articleId = articleId;
    }
}
