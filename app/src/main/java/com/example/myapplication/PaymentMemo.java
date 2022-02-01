package com.example.myapplication;

public class PaymentMemo {

    // All Data of the dataset. Pls add what ya need. this is probably not the complete list
    private String product;
    //private int quantity;
    private String cost;
    private long id;

    public PaymentMemo (String cost, String product) {
        this.cost = cost;
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    /*
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    */

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String output = cost + " x " + product;

        return output;
    }
}

