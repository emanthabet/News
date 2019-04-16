package com.route.news;

import android.util.Log;
import android.widget.AdapterView;

import com.route.news.APIs.ApiManager;
import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.APIs.Models.NewsContent.NewsResponse;
import com.route.news.APIs.Models.NewsSources.SourcesItem;
import com.route.news.APIs.Models.NewsSources.SourcesResponse;
import com.route.news.Database.Mydatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    List<SourcesItem> sourcesList;
    List<Favourite>fav=new ArrayList<>();
    String language;
    private static String ApiKey = "ddca8b65082f4d5896d54033fe8f9fe3";

    public NewsRepository(String language) {
        this.language = language;
        sourcesList = new ArrayList<>();
    }


    public void getAllSources(final OnSourcePreparedListener onSourcePreparedListener) {
        ApiManager.getApis()
                .getAllNewsSources(ApiKey, language)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                        if (response.isSuccessful() && "ok".equals(response.body().getStatus())) {

                            sourcesList = response.body().getSources();
                            //hena ana ana msh ha set eladapter l2ny malesh da3wa beh ana hb3t llactivity ellist w hea tt3amel
                            if (onSourcePreparedListener != null) {
                                onSourcePreparedListener.onsourceprepared(sourcesList);
                            }
                            insertintosourcethread myinsertintonewthread = new insertintosourcethread(response.body().getSources());
                            myinsertintonewthread.start();

                        }
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                        //hn handle mn eldatabase
                        Thread th = new getSourcesFromDatabase(onSourcePreparedListener);
                        th.start();
                    }
                });
    }


    public void getAllNews(String lang, final String sourceid, final OnNewsPreparedListner onNewsPreparedListner) {
        ApiManager.getApis()
                .getAllNewsContent(ApiKey, lang, sourceid)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful() && "ok".equals(response.body().getStatus())) {
                            if (onNewsPreparedListner != null) {
                                Thread th = new insertintonewsthread(response.body().getArticles());
                                th.start();
                                onNewsPreparedListner.onnewsprepared(response.body().getArticles());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        Thread th = new getNewsFromDatabase(onNewsPreparedListner, sourceid);//source id begelye lma b3ml newsrepos.getallnews(sourceitem.getid)
                        th.start();
                    }
                });
    }

    public void addfavnews(Favourite fav) {
                Thread th = new insertfavintonewsthread(fav);
                th.start();

    }

    public void getallmyfavnews(OnFavNewsPreparedListner onfavNewsPreparedListner){
        if(onfavNewsPreparedListner!=null){
        Thread th=new getFavNewsFromDatabase(onfavNewsPreparedListner);
        th.start();}
    }

    public void removefav(Favourite fav){
        Thread th=new Removefavthread(fav);
        th.start();
    }


    public interface OnSourcePreparedListener {
         void onsourceprepared(List<SourcesItem> sourcesItemList);
    }


    public interface OnNewsPreparedListner {
        public void onnewsprepared(List<ArticlesItem> newslist);
    }
    public interface OnFavNewsPreparedListner {
        public void onfavnewsprepared(List<Favourite> newslist);
    }


    class insertintosourcethread extends Thread {
        List<SourcesItem> sourcesItemList;

        public insertintosourcethread(List<SourcesItem> sourcesItemList) {
            this.sourcesItemList = sourcesItemList;
        }

        @Override
        public void run() {
            Mydatabase.getInstance().SourcesDaoobj().AddSource(sourcesItemList);
            Log.e("sourcethread", "insertion success");
        }
    }

    class getSourcesFromDatabase extends Thread {
        OnSourcePreparedListener listener;

        public getSourcesFromDatabase(OnSourcePreparedListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            List<SourcesItem> items = Mydatabase.getInstance().SourcesDaoobj().getallsources();
            listener.onsourceprepared(items);
            Log.e("sourcethread", "getsources success");
        }
    }


    class insertintonewsthread extends Thread {
        List<ArticlesItem> articlesItems;

        public insertintonewsthread(List<ArticlesItem> articlesItems) {
            this.articlesItems = articlesItems;
        }

        @Override
        public void run() {
            for (int i = 0; i < articlesItems.size(); i++) {
                ArticlesItem item = articlesItems.get(i);
                item.setSourceid(item.getSource().getId());
                item.setSourcename(item.getSource().getName());
            }
            Mydatabase.getInstance().newsDaoobj().Addnews(articlesItems);
            Log.e("sourcethread", "insertion success");
        }

    }

    class getNewsFromDatabase extends Thread {
        OnNewsPreparedListner listener;
        String sourceid;

        public getNewsFromDatabase(OnNewsPreparedListner listener, String sourceid) {
            this.listener = listener;
            this.sourceid = sourceid;
        }

        @Override
        public void run() {
            List<ArticlesItem> items = Mydatabase.getInstance().newsDaoobj().getallnewsbysourceid(sourceid);
            listener.onnewsprepared(items);
            Log.e("sourcethread", "getsources success");
        }
    }

    class insertfavintonewsthread extends Thread {
        Favourite favourites;

        public insertfavintonewsthread(Favourite favourites) {
            this.favourites = favourites;
        }

        @Override
        public void run() {
            Mydatabase.getInstance().FavDaoobj().AddFavnews(favourites);
            Log.e("favnewsthread", "insertion success");
        }

    }

    class getFavNewsFromDatabase extends Thread {
        OnFavNewsPreparedListner listener;

        public getFavNewsFromDatabase(OnFavNewsPreparedListner listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            List<Favourite> items = Mydatabase.getInstance().FavDaoobj().getallfav();
            listener.onfavnewsprepared(items);
            Log.e("favnewsthread", "getfav success");
        }
    }

    class Removefavthread extends Thread {
        Favourite favourites;

        public Removefavthread(Favourite favourites) {
            this.favourites = favourites;
        }

        @Override
        public void run() {
            Mydatabase.getInstance().FavDaoobj().RemoveFav(favourites);
            Log.e("removefavnewsthread", "remove success");
        }

    }
}
