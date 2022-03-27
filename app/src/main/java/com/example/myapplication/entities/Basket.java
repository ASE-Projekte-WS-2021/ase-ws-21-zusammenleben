package com.example.myapplication.entities;

public class Basket {
    String title;
    String subtitle;
    String notice;

    public Basket(String title, String subtitle, String notice) {
        this.title = title;
        this.subtitle = subtitle;
        this.notice = notice;

    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getNotice() {
        return notice;
    }
}
