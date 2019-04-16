package com.route.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.APIs.Models.NewsSources.SourcesItem;
import com.route.news.Adapter.FavouriteAdapter;
import com.route.news.Adapter.NewsAdapter;
import com.route.news.Base.BaseActivity;
import com.route.news.Database.Mydatabase;

import java.util.List;

public class HomeActivity extends BaseActivity {

    TabLayout tablayout;
    NewsRepository newsRepository;
    String lang = "en";
    RecyclerView recyclerView;
    NewsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView heart;
    ImageButton fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("News");
        setSupportActionBar(toolbar);
        heart = findViewById(R.id.emptyheart);
        tablayout = findViewById(R.id.tablayoutsource);
        recyclerView = findViewById(R.id.contentRecyclerView);
        adapter = new NewsAdapter(null);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        fav = findViewById(R.id.favbutton);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FavouriteActivity.class));
            }
        });
        newsRepository = new NewsRepository(lang);

        newsRepository.getAllSources(myonSourcePreparedListener);
        adapter.setOnItemClickListner(new NewsAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(boolean state, Favourite fav) {
                if (state) {
                    newsRepository.addfavnews(fav);
                }
                else
                {
                    newsRepository.removefav(fav);
                }

            }
        });
    }


    NewsRepository.OnSourcePreparedListener myonSourcePreparedListener = new NewsRepository.OnSourcePreparedListener() {
        @Override
        public void onsourceprepared(final List<SourcesItem> sourcesItemList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addSourceToTabLayout(sourcesItemList);
                }
            });
        }
    };


    NewsRepository.OnNewsPreparedListner myonNewsPreparedListner = new NewsRepository.OnNewsPreparedListner() {
        @Override
        public void onnewsprepared(final List<ArticlesItem> newslist) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.changedata(newslist);
                }
            });
        }

    };


    //creating tabs
    private void addSourceToTabLayout(final List<SourcesItem> sourcesItemList) {
        if (sourcesItemList == null)
            return;//ya3ney msh h3ml 7aga

        for (int i = 0; i < sourcesItemList.size(); i++) {
            TabLayout.Tab tab = tablayout.newTab();
            tab.setText(sourcesItemList.get(i).getName());
            tab.setTag(sourcesItemList.get(i));
            tablayout.addTab(tab);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                newsRepository.getAllNews(lang, sourcesItem.getId(), myonNewsPreparedListner);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                newsRepository.getAllNews(lang, sourcesItem.getId(), myonNewsPreparedListner);
            }
        });
        // 6b mn 3'er ma ados 3ala eltab
        tablayout.getTabAt(0).select();
    }


}




