package br.edu.ifsp.dmo.app14_sqlite.presenter;

import android.os.Bundle;
import android.util.Log;

import br.edu.ifsp.dmo.app14_sqlite.model.dao.ArticleDaoSQLite;
import br.edu.ifsp.dmo.app14_sqlite.model.dao.ArticleDaoSingleton;
import br.edu.ifsp.dmo.app14_sqlite.model.dao.IArticleDao;
import br.edu.ifsp.dmo.app14_sqlite.model.entities.Article;
import br.edu.ifsp.dmo.app14_sqlite.mvp.ArticleDetailsMVP;
import br.edu.ifsp.dmo.app14_sqlite.utils.Constant;

public class ArticleDetailsPresenter implements ArticleDetailsMVP.Presenter {

    private ArticleDetailsMVP.View view;
    private Article article;
    private IArticleDao dao;

    public ArticleDetailsPresenter(ArticleDetailsMVP.View view) {
        this.view = view;
        article = null;
        //dao = ArticleDaoSingleton.getInstance();
        dao = new ArticleDaoSQLite(view.getContext());
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate() {
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constant.ATTR_TITLE);
            article = dao.findByTitle(title);
            view.updateUI(article.getTitle(), article.getUrl());
        }
    }

    @Override
    public void saveArticle(String title, String url) {

        if(article == null){
            //New article
            article = new Article(url, title);
            dao.create(article);
            view.showToast("Novo artigo adicionado com sucesso.");
            view.close();
        }else{
            //Update a existent article
            String oldTitle = article.getTitle();
            Article newArticle = new Article(url, title, article.isFavorite());
            if(dao.update(oldTitle, newArticle)){
                view.showToast("Artigo atualizado com sucesso.");
                view.close();
            }else{
                view.showToast("Erro ao atualizar o artigo.");
            }
        }
    }
}
