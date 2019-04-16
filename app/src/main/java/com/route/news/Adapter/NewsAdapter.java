package com.route.news.Adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<ArticlesItem> newsList;
    OnItemClickListner onItemClickListner;
    FavouriteAdapter adapter;

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
    public NewsAdapter(List<ArticlesItem> newsList) {
        this.newsList = newsList;
    }


    View view;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ArticlesItem articlesItem = newsList.get(i);
        viewHolder.title.setText(articlesItem.getTitle());

        if (articlesItem.getSource() != null)
            viewHolder.source.setText(articlesItem.getSource().getName());
        else
            viewHolder.source.setText(articlesItem.getSourcename());
        viewHolder.date.setText(articlesItem.getPublishedAt());

        Glide.with(viewHolder.itemView).load(articlesItem.getUrlToImage()).into(viewHolder.image);
        viewHolder.heart.setTag(1);
        viewHolder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListner!=null) {
                    Favourite favourite = new Favourite();
                    favourite.setSourceid(articlesItem.getSourceid());
                    favourite.setSourcename(articlesItem.getSourcename());
                    favourite.setTitle(articlesItem.getTitle());
                    favourite.setUrlToImage(articlesItem.getUrlToImage());
                    favourite.setPublishedAt(articlesItem.getPublishedAt());
                    if(viewHolder.heart.getTag().equals(1)){
                        viewHolder.heart.setImageResource(R.drawable.ic_heart);
                        onItemClickListner.OnItemClick(true,favourite);
                        viewHolder.heart.setTag(2);


                 }
                    else if (viewHolder.heart.getTag().equals(2))
                    {
                        viewHolder.heart.setImageResource(R.drawable.ic_empty_heart);
                        onItemClickListner.OnItemClick(false,favourite);
                        viewHolder.heart.setTag(1);
                    }
                }
            }
        });

        }




    public void changedata(List<ArticlesItem> articlesItemList) {
        this.newsList = articlesItemList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (newsList == null)
        { return 0;}
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,heart;
        TextView title, date, source;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            source = itemView.findViewById(R.id.sourcename);
            image = itemView.findViewById(R.id.image);
            heart=itemView.findViewById(R.id.emptyheart);
        }
    }

    public interface OnItemClickListner{
        public void OnItemClick(boolean state,Favourite fav);
    }
}
