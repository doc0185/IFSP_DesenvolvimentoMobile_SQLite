package br.edu.ifsp.dmo.app14_sqlite.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.dmo.app14_sqlite.model.dao.ArticleDaoSQLite;
import br.edu.ifsp.dmo.app14_sqlite.model.dao.ArticleDaoSingleton;
import br.edu.ifsp.dmo.app14_sqlite.model.dao.IArticleDao;
import br.edu.ifsp.dmo.app14_sqlite.model.entities.Article;
import br.edu.ifsp.dmo.app14_sqlite.mvp.MainMVP;
import br.edu.ifsp.dmo.app14_sqlite.utils.Constant;
import br.edu.ifsp.dmo.app14_sqlite.view.ArticleDetailsActivity;
import br.edu.ifsp.dmo.app14_sqlite.view.RecyclerViewItemClickListener;
import br.edu.ifsp.dmo.app14_sqlite.view.adapter.ItemPocketRecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter {

    private MainMVP.View view;
    private IArticleDao dao;
    Article article;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        //dao = ArticleDaoSingleton.getInstance();
        dao = new ArticleDaoSQLite(view.getContext());

    }

    @Override
    public void deatach() {
        view = null;
    }

    @Override
    public void openDetails() {
        Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openDetails(Article article) {
        Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
        intent.putExtra(Constant.ATTR_TITLE, article.getTitle());
        view.getContext().startActivity(intent);
    }

    @Override
    public void populateList(RecyclerView recyclerView) {
        /*ArrayAdapter<Article> adapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                dao.findAll());*/
        /*ItemPocketAdapter adapter = new ItemPocketAdapter(
                view.getContext(),
                dao.findAll(),
                this);
        listView.setAdapter(adapter);*/
        ItemPocketRecyclerAdapter adapter = new
                ItemPocketRecyclerAdapter(view.getContext(), dao.findAll(), this);
        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                article = dao.findAll().get(position);
                openDetails(article);
            }
        });
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void favoriteArticle(Article article) {
        article.setFavorite(!article.isFavorite());
        dao.update(article.getTitle(), article);
    }
}