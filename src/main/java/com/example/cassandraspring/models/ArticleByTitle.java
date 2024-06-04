package com.example.cassandraspring.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("article_by_title")
public class ArticleByTitle {
    @PrimaryKey
    private ArticleByTitleKey key;
    @Column
    private Double rate;

    public ArticleByTitle(ArticleByTitleKey key, Double rate) {
        this.key = key;
        this.rate = rate;
    }
}
