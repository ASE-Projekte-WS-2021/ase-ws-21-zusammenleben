package Entities;

public class Basket {
    String title;
    String subtitle;
    String notice;
    String flatID;

    public Basket(String title, String subtitle, String notice, String flatID) {
        this.title = title;
        this.subtitle = subtitle;
        this.notice = notice;
        this.flatID = flatID;
    }

    public Basket(){
    }

    public String getFlatID(){
        return flatID;
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
