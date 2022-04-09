package Entities;

public class Basket {
    String title;
    String currentUser;
    String timestamp;
    String flatID;
    String basketID;

    public Basket(String title, String currentUser, String flatID, String basketID) {
        this.title = title;
        this.currentUser = currentUser;
        this.flatID = flatID;
        this.basketID = basketID;
    }

    public Basket(){
    }

    public String getBasketID(){
        return basketID;
    }

    public void setBasketID(String id){
        this.basketID = id;
    }

    public String getFlatID(){
        return flatID;
    }

    public String getTitle() {
        return title;
    }

    public String getCurrentUser() {
        return currentUser;
    }

}
