package com.route.news.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.APIs.Models.NewsSources.SourcesItem;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void Addnews(List<ArticlesItem> articlesItems);

    @Query("Select * From ArticlesItem where sourceid=:SourceId;")
    List<ArticlesItem> getallnewsbysourceid(String SourceId);

     @Update
    public void updatenews(ArticlesItem articlesItem);

}
