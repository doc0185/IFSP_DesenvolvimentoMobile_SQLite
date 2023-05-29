package br.edu.ifsp.dmo.app14_sqlite.model.entities;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String url;
    private String title;
    private boolean favorite;

    //Relationships
    private List<Tag> tags;

    private void init(){
        tags = new ArrayList<>();
    }

    public Article(String url, String title) {
        this.url = url;
        this.title = title;
        init();
    }

    public Article(String url, String title, boolean favorite) {
        this.url = url;
        this.title = title;
        this.favorite = favorite;
        init();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public boolean removeTag(Tag tag){
        return this.tags.remove(tag);
    }

    public List<Tag> getTags(){
        return tags;
    }


    @NonNull
    @Override
    public String toString() {
        return "Title: " + title;
    }
}