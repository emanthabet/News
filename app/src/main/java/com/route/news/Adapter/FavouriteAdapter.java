package com.route.news.Adapter;

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

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    List<Favourite> FavNews;
    OnItemClickListner onItemClickListner;



    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
    public FavouriteAdapter(List<Favourite> FavNews) {
        this.FavNews = FavNews;
    }


    View view;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_fav_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Favourite favourite = FavNews.get(i);
        viewHolder.title.setText(favourite.getTitle());
        viewHolder.source.setText(favourite.getSourcename());
        viewHolder.date.setText(favourite.getPublishedAt());
        Glide.with(viewHolder.itemView).load(favourite.getUrlToImage()).into(viewHolder.image);
        viewHolder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListner!=null) {
                    {
                        viewHolder.heart.setImageResource(R.drawable.ic_empty_heart);
                        onItemClickListner.OnItemClick(favourite);

                        }
                }
            }
        });



    }

    public void changedata(List<Favourite> FavNews) {
        this.FavNews = FavNews;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (FavNews == null)
            return 0;
        else {
            return FavNews.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, heart;
        TextView title, date, source;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            heart = itemView.findViewById(R.id.heart);
            source = itemView.findViewById(R.id.sourcename);

        }
    }

    public interface OnItemClickListner{
        public void OnItemClick(Favourite fav);
    }
}
