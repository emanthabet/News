package com.route.news.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.route.news.APIs.Models.NewsSources.SourcesItem;

import java.util.List;


@Dao
public interface SourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void AddSource(List<SourcesItem> sourcesItems);

    @Query("Select * From SourcesItem;")
     List<SourcesItem> getallsources();
}
