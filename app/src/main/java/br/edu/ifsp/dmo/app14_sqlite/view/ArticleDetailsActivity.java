package br.edu.ifsp.dmo.app14_sqlite.view;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.dmo.app14_sqlite.R;
import br.edu.ifsp.dmo.app14_sqlite.mvp.ArticleDetailsMVP;
import br.edu.ifsp.dmo.app14_sqlite.presenter.ArticleDetailsPresenter;

public class ArticleDetailsActivity extends AppCompatActivity
        implements ArticleDetailsMVP.View, View.OnClickListener {

    private ArticleDetailsMVP.Presenter presenter;
    private EditText urlEditText;
    private EditText titleEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);

        presenter = new ArticleDetailsPresenter(this);
        findViews();
        setListener();
        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.verifyUpdate();
    }

    @Override
    protected void onDestroy() {
        presenter.deatach();
        super.onDestroy();
    }

    public Context getContext(){
        return this;
    }

    @Override
    public void onClick(View v) {
        if(v == saveButton){
            presenter.saveArticle(
                    titleEditText.getText().toString(),
                    urlEditText.getText().toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI(String title, String url) {
        titleEditText.setText(title);
        urlEditText.setText(url);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        presenter.deatach();
        finish();
    }

    private void findViews(){
        urlEditText = findViewById(R.id.edittext_url_details);
        titleEditText = findViewById(R.id.edittext_title_details);
        saveButton = findViewById(R.id.button_save_article);
    }

    private void setListener(){
        saveButton.setOnClickListener(this);
    }

    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
