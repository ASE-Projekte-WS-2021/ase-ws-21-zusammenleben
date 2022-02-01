package com.example.myapplication;

public class PaymentMemo {

    // All Data of the dataset. Pls add what ya need. this is probably not the complete list
    private String product;
    //private int quantity;
    private double cost;
    private long id;

    public PaymentMemo (String product, /*int quantity,*/ double cost, long id) {
        this.product = product;
        //this.quantity = quantity;
        this.cost = cost;
        this.id = id;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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
