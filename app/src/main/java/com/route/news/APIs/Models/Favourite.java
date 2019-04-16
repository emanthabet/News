package com.route.news.APIs.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.route.news.APIs.Models.NewsSources.SourcesItem;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys=@ForeignKey(entity=SourcesItem.class,
        parentColumns="id",
        childColumns = "sourceid",
        onDelete = CASCADE),
        indices = {@Index(value = "sourceid",unique = false)})
public class Favourite {
     @ColumnInfo
    private String publishedAt;
     @ColumnInfo
    private String urlToImage;
     @NonNull
     @PrimaryKey
     @ColumnInfo
    private String title;
     @ColumnInfo
    String sourcename;
     @ColumnInfo
    String sourceid;

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    boolean heart;

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    int articleid;


    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
}
