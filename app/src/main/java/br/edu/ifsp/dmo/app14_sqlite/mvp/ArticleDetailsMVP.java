package br.edu.ifsp.dmo.app14_sqlite.mvp;

import android.content.Context;
import android.os.Bundle;

public interface ArticleDetailsMVP {
    interface View{
        void updateUI(String title, String url);
        Context getContext();


        Bundle getBundle();

        void showToast(String message);

        void close();
    }

    interface Presenter{
        void deatach();

        void verifyUpdate();

        void saveArticle(String title, String url);
    }
}
