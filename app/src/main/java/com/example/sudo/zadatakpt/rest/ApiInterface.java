package com.example.sudo.zadatakpt.rest;

import com.example.sudo.zadatakpt.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("articles")
    Call<NewsResponse> getNews(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy, @Query("source") String source);
}
