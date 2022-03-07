package com.example.myapplication;

public class notes {

    private String title;
    private String subtitle;
    private String text;

    public notes(){

    }

    public notes(String title, String subtitle, String text){

        this.title=title;
        this.subtitle=subtitle;
        this.text=text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
