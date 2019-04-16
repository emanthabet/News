package com.route.news.APIs.Models.NewsContent;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.route.news.APIs.Models.NewsSources.SourcesItem;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = SourcesItem.class,
        parentColumns = "id",
        childColumns = "sourceid",
        onDelete = CASCADE),
        indices = {@Index(value = "sourceid", unique = false)})//elsource id msh unique
// elnews no 1momkin tkon gya mn source no.10 w news.2 gaya mn source 10 bardo
public class ArticlesItem {

    @ColumnInfo
    @SerializedName("publishedAt")
    private String publishedAt;

    @ColumnInfo
    @SerializedName("author")
    private String author;






    @ColumnInfo
    @SerializedName("urlToImage")
    private String urlToImage;

    @ColumnInfo
    @SerializedName("description")
    private String description;

    @Ignore
    @SerializedName("source")
    private SourcesItem source;

    String sourcename;
    String sourceid;

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    boolean heart;

    @ColumnInfo
    @SerializedName("title")
    private String title;

    @ColumnInfo
    @PrimaryKey
    @NonNull
    @SerializedName("url")
    private String url;

    @ColumnInfo
    @SerializedName("content")
    private String content;

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setSource(SourcesItem source) {
        this.source = source;
    }

    public SourcesItem getSource() {
        return source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    @Override
    public String toString() {
        return
                "ArticlesItem{" +
                        "publishedAt = '" + publishedAt + '\'' +
                        ",author = '" + author + '\'' +
                        ",urlToImage = '" + urlToImage + '\'' +
                        ",description = '" + description + '\'' +
                        ",source = '" + source + '\'' +
                        ",title = '" + title + '\'' +
                        ",url = '" + url + '\'' +
                        ",content = '" + content + '\'' +
                        "}";
    }
}