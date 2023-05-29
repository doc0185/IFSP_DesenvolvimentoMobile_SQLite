package br.edu.ifsp.dmo.app14_sqlite.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifsp.dmo.app14_sqlite.R;
import br.edu.ifsp.dmo.app14_sqlite.mvp.MainMVP;
import br.edu.ifsp.dmo.app14_sqlite.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    private MainMVP.Presenter presenter;
    private FloatingActionButton actionButton;
    //private ListView listView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
        presenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.populateList(recyclerView);
    }

    @Override
    protected void onDestroy() {
        presenter.deatach();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    // infla o action menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //case R.id.new_tag:
                //criar nova tag
                //break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void findViews(){
        actionButton = findViewById(R.id.fab_add_article);
        //listView = findViewById(R.id.list_article);
        recyclerView = findViewById(R.id.recyclerview_article);
    }

    private void setListener(){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openDetails();
            }
        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article a = (Article) listView.getAdapter().getItem(position);
                presenter.openDetails(a);
            }
        });*/
    }

}