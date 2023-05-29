package br.edu.ifsp.dmo.app14_sqlite.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.dmo.app14_sqlite.model.entities.Article;

public interface MainMVP {

        interface View{
            Context getContext();
        }

        interface Presenter{
            void deatach();

            void openDetails();

            void openDetails(Article article);

            void populateList(RecyclerView recyclerView);

            void favoriteArticle(Article article);
        }
    }

