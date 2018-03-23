package com.example.sudo.zadatakpt.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NewsResponse {

    @SerializedName("articles")
    private List<News> news;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
