package com.example.cassandraspring.controllers;

import com.example.cassandraspring.models.Article;
import com.example.cassandraspring.models.ArticleByRate;
import com.example.cassandraspring.models.ArticleByTitle;
import com.example.cassandraspring.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping()
    public List<Article> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public Article getById(@PathVariable UUID id) {
        return articleService.getById(id);
    }

    @GetMapping("/title")
    public List<ArticleByTitle> getByTitle(@RequestParam String title) {
        return articleService.getByTitle(title);
    }

    @GetMapping("/rate")
    public List<ArticleByRate> getByRate(@RequestParam Double min,
                                         @RequestParam Double max) {
        return articleService.getByRate(min, max);
    }

    @PostMapping()
    public Article create(@RequestBody Article table) {
        return articleService.create(table);
    }

    @PutMapping()
    public Article update(@RequestBody Article table) {
        return articleService.update(table);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        articleService.deleteById(id);
    }
}
