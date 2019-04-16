package com.route.news.APIs;

import com.route.news.APIs.Models.NewsContent.NewsResponse;
import com.route.news.APIs.Models.NewsSources.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {
    @GET ("sources")
    Call<SourcesResponse> getAllNewsSources(@Query("apiKey")String apiKey,@Query("language")String language);

    @GET ("everything")
    Call<NewsResponse> getAllNewsContent(@Query("apiKey")String apiKey, @Query("language")String language,@Query("sources")String SourceId);
}
