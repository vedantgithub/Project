package com.example.topnews;

public class mynews {
    String Title;
    String news;

    public mynews() {
    }

    public mynews(String title, String news) {
        this.Title = Title;
        this.news = news;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
