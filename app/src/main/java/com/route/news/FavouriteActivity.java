package com.route.news;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.Adapter.FavouriteAdapter;
import com.route.news.Adapter.NewsAdapter;
import com.route.news.Base.BaseActivity;
import com.route.news.Database.Mydatabase;

import java.util.List;

public class FavouriteActivity extends BaseActivity {
    RecyclerView recyclerView;
    FavouriteAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String lang = "en";
    NewsRepository repository;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Fav");
        swipeRefreshLayout=findViewById(R.id.swipetorefresh);
        recyclerView = findViewById(R.id.contentRecyclerView);
        adapter = new FavouriteAdapter(null);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        repository = new NewsRepository(lang);
        getallmyfavnews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getallmyfavnews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter.setOnItemClickListner(new FavouriteAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(Favourite fav) {
                repository.removefav(fav);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        getallmyfavnews();
    }


    public void getallmyfavnews() {
        repository.getallmyfavnews(new NewsRepository.OnFavNewsPreparedListner() {
            @Override
            public void onfavnewsprepared(final List<Favourite> newslist) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.changedata(newslist);
                    }
                });
            }
        });
    }
}
