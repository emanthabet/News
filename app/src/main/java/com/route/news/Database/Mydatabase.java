package com.route.news.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.APIs.Models.NewsSources.SourcesItem;
import com.route.news.Database.DAOs.FavsDao;
import com.route.news.Database.DAOs.NewsDao;
import com.route.news.Database.DAOs.SourcesDao;


@Database(entities = {SourcesItem.class,ArticlesItem.class, Favourite.class},version = 8,exportSchema = false)
public abstract class Mydatabase extends RoomDatabase {


    public abstract SourcesDao SourcesDaoobj();
    public abstract NewsDao newsDaoobj();
    public abstract FavsDao FavDaoobj();


    private static Mydatabase mydatabase;

    public static void init(Context context){
        if(mydatabase==null)
        { mydatabase= Room.databaseBuilder(context.getApplicationContext(),Mydatabase.class,"database")
                .fallbackToDestructiveMigration()
                .build();}
    }
    public static Mydatabase getInstance()
    {
           return mydatabase;
    }
}
