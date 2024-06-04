package com.example.cassandraspring.repositories;

import com.example.cassandraspring.models.ArticleByTitle;
import com.example.cassandraspring.models.ArticleByTitleKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleByTitleRepository extends CassandraRepository<ArticleByTitle, ArticleByTitleKey> {
    @Query(allowFiltering = false)
    List<ArticleByTitle> findByKey_TitleAndKey_ArticleId(String title, UUID articleId);

    @Query(allowFiltering = false)
    List<ArticleByTitle> findAllByKey_Title(String title);
}
