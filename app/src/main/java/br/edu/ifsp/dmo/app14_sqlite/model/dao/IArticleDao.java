package br.edu.ifsp.dmo.app14_sqlite.model.dao;

import java.util.List;

import br.edu.ifsp.dmo.app14_sqlite.model.entities.Article;
import br.edu.ifsp.dmo.app14_sqlite.model.entities.Tag;

public interface IArticleDao {
        void create(Article article);

        boolean update(String oldTitle, Article article);

        boolean delete(Article article);

        Article findByTitle(String title);

        List<Article> findByTag(Tag tag);

        List<Article> findAll();

}
