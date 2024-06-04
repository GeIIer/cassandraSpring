package com.example.cassandraspring.models;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("article_by_rate")
public class ArticleByRate {
    @PrimaryKey
    private ArticleByRateKey key;
    @Column
    private String title;

    public ArticleByRate(ArticleByRateKey key, String title) {
        this.key = key;
        this.title = title;
    }
}
