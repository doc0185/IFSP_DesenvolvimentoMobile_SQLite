package br.edu.ifsp.dmo.app14_sqlite.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dmo.app14_sqlite.model.entities.Article;
import br.edu.ifsp.dmo.app14_sqlite.model.entities.Tag;
import br.edu.ifsp.dmo.app14_sqlite.utils.Constant;

public class ArticleDaoSQLite implements IArticleDao{

    private SQLiteHelper mHelper;
    private SQLiteDatabase mDatabase;

    public ArticleDaoSQLite (Context context){
        mHelper = new SQLiteHelper(context);

    }

    public static String createTable(){
        String sql = "CREATE TABLE " + Constant.ENTITY_ARTICLE + "(";
        sql += Constant.ATTR_ID + " INTEGER PRIMARY KEY";
        sql += Constant.ATTR_TITLE + " TEXT NOT NULL, ";
        sql += Constant.ATTR_URL + " TEXT NOT NULL, ";
        sql += Constant.ATTR_FAVORITE + " INTEGER NOT NULL ";
        sql += "CHECK(" + Constant.ATTR_FAVORITE + " IN(0, 1))";
        return sql;
    }

    public static String createTablev1(){
        String sql = "CREATE TABLE " + Constant.ENTITY_ARTICLE + "(";
        sql += Constant.ATTR_TITLE + " TEXT NOT NULL, ";
        sql += Constant.ATTR_URL + " TEXT NOT NULL, ";
        sql += Constant.ATTR_FAVORITE + " INTEGER NOT NULL ";
        sql += "CHECK(" + Constant.ATTR_FAVORITE + " IN(0, 1))";
        return sql;
    }

    @Override
    public void create(Article article) {
        ContentValues values = new ContentValues();
        values.put(Constant.ATTR_TITLE, article.getTitle());
        values.put(Constant.ATTR_URL, article.getUrl());
        values.put(Constant.ATTR_FAVORITE, article.isFavorite()?1:0);

        mDatabase = mHelper.getWritableDatabase();
        long lines = mDatabase.insert(Constant.ENTITY_ARTICLE, null, values);
        // long lines apenas para saber que podemos usar o retorno, se houver algo errado, retorna -1
        mDatabase.close();

    }

    @Override
    public boolean update(String oldTitle, Article article) {
        boolean deuCerto = true;
        ContentValues values = new ContentValues();
        values.put(Constant.ATTR_TITLE, article.getTitle());
        values.put(Constant.ATTR_URL, article.getUrl());
        values.put(Constant.ATTR_FAVORITE, article.isFavorite()?1:0);

        String where = Constant.ATTR_TITLE + " = ? ";
        String whereArgs[] = {oldTitle};

        try{
            mDatabase = mHelper.getWritableDatabase();
            mDatabase.update(
                    Constant.ENTITY_ARTICLE,
                    values,
                    where,
                    whereArgs
            );
        } catch (Exception e){
            deuCerto = false;
        } finally{
            mDatabase.close();
        }

        return deuCerto;
    }

    @Override
    public boolean delete(Article article) {
        return false;
    }

    @Override
    public Article findByTitle(String title) {
        Article article = null;
        String columns[] = new String[]{
                Constant.ATTR_TITLE,
                Constant.ATTR_URL,
                Constant.ATTR_FAVORITE
        };

        String selection = Constant.ATTR_TITLE + " = ? ";
        String selectionArgs[] = {title};

        try{
            mDatabase = mHelper.getReadableDatabase();
            Cursor cursor = mDatabase.query(
                    Constant.ENTITY_ARTICLE,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if(cursor.moveToNext()){
                article = new Article(
                        cursor.getString(1),
                        cursor.getString(0),
                        cursor.getInt(2)==1?true:false
                );
            }
            cursor.close();
        }catch (Exception e){
            article = null;
        }

        return article;
    }

    @Override
    public List<Article> findByTag(Tag tag) {
        return null;
    }

    @Override
    public List<Article> findAll() {
        List<Article> list = new ArrayList<>();
        Article article;
        Cursor cursor;

        String columns[] = new String[]{
                Constant.ATTR_TITLE,
                Constant.ATTR_URL,
                Constant.ATTR_FAVORITE
        };

        mDatabase = mHelper.getReadableDatabase();
        cursor = mDatabase.query(
                Constant.ENTITY_ARTICLE,
                columns,
                null,
                null,
                null,
                null,
                Constant.ATTR_TITLE
        );

        while(cursor.moveToNext()){
            article = new Article(
                    cursor.getString(1),
                    cursor.getString(0),
                    cursor.getInt(2)==1?true:false
            );
            list.add(article);
        }

        cursor.close();
        mDatabase.close();
        return list;
    }
}
