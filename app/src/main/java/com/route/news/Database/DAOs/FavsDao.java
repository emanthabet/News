package com.route.news.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.route.news.APIs.Models.Favourite;
import com.route.news.APIs.Models.NewsContent.ArticlesItem;
import com.route.news.APIs.Models.NewsSources.SourcesItem;

import java.util.List;

@Dao
public interface FavsDao {
    @Query("Select * From Favourite;")
    List<Favourite> getallfav();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void AddFavnews(Favourite favourites);

    @Delete
    public void RemoveFav(Favourite favourite);

}
