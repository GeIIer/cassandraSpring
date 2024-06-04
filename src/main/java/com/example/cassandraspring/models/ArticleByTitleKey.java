package com.example.cassandraspring.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Getter
@Setter
@PrimaryKeyClass
public class ArticleByTitleKey {
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String title;

    @PrimaryKeyColumn(name = "article_id", ordinal = 1, ordering = Ordering.DESCENDING, type = PrimaryKeyType.PARTITIONED)
    private UUID articleId;

    public ArticleByTitleKey(String title, UUID articleId) {
        this.title = title;
        this.articleId = articleId;
    }
}
