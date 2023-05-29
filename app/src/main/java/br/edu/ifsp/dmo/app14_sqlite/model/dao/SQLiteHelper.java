package br.edu.ifsp.dmo.app14_sqlite.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.ifsp.dmo.app14_sqlite.utils.Constant;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my_pocket.db";
    public static final int DATABASE_VERSION = 2;

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = ArticleDaoSQLite.createTable();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;

        switch (oldVersion){
            case 1:
                //renomeia a table article para article_old
                sql = "ALTER TABLE " + Constant.ENTITY_ARTICLE;
                sql += "RENAME TO " + Constant.ENTITY_ARTICLE + "_old";
                db.execSQL(sql);

                //criar a nova table article
                db.execSQL(ArticleDaoSQLite.createTable());

                //insere todos os dados já cadastrados na tabela nova
                sql = "INSERT INTO " + Constant.ENTITY_ARTICLE + " (";
                sql += Constant.ATTR_TITLE + ", ";
                sql += Constant.ATTR_URL + ", ";
                sql += Constant.ATTR_FAVORITE + ") ";
                sql += "SELECT ";
                sql += Constant.ATTR_TITLE + ", ";
                sql += Constant.ATTR_URL + ", ";
                sql += Constant.ATTR_FAVORITE + ", ";
                sql += "FROM " + Constant.ENTITY_ARTICLE + "_old";
                db.execSQL(sql);

                //apagar tabela atiga
                sql = "DROP TABLE " + Constant.ENTITY_ARTICLE + "_old";
                db.execSQL(sql);

                break;
                //se houvessem mais versões, seria interessante retirar o break, para que
                //depois que atualizasse da 1 pra 2, poderia seguir da 2 para 3, por exemplo.
        }
    }
}
