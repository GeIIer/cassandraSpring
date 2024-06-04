package com.example.cassandraspring.repositories;

import com.example.cassandraspring.models.Article;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface ArticleRepository extends CassandraRepository<Article, UUID> {

}
