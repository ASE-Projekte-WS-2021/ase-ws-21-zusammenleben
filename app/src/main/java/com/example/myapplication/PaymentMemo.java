package com.example.myapplication;

public class PaymentMemo {

    // All Data of the dataset. Pls add what ya need. this is probably not the complete list
    private String purpose;
    private String cost;
    private String name;

    public PaymentMemo (String cost, String purpose, String name) {
        this.cost = cost;
        this.purpose = purpose;
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName(){return name;}

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setName(String name) {this.name = name;}

}



