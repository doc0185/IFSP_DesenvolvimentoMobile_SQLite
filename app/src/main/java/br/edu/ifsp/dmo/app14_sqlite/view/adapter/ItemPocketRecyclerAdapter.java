package br.edu.ifsp.dmo.app14_sqlite.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.dmo.app14_sqlite.R;
import br.edu.ifsp.dmo.app14_sqlite.model.entities.Article;
import br.edu.ifsp.dmo.app14_sqlite.mvp.MainMVP;
import br.edu.ifsp.dmo.app14_sqlite.view.RecyclerViewItemClickListener;

public class ItemPocketRecyclerAdapter extends
        RecyclerView.Adapter<ItemPocketRecyclerAdapter.ViewHolder>{

    private Context context;
    private MainMVP.Presenter presenter;
    private List<Article> data;
    private static RecyclerViewItemClickListener clickListener;

    public ItemPocketRecyclerAdapter(Context context, List<Article> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = data.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.urlTextView.setText(article.getUrl());
        if(article.isFavorite()){
            holder.favoriteImageView.setColorFilter(context.getColor(R.color.RED));
        }else{
            holder.favoriteImageView.setColorFilter(context.getColor(R.color.gray));
        }
        holder.favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartClick(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(RecyclerViewItemClickListener listener){
        clickListener = listener;
    }

    private void heartClick(Article article){
        presenter.favoriteArticle(article);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView titleTextView;
        public TextView urlTextView;
        public ImageView favoriteImageView;

        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_title_listitem);
            urlTextView = itemView.findViewById(R.id.text_url_listitem);
            favoriteImageView = itemView.findViewById(R.id.image_favorite_listitem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
