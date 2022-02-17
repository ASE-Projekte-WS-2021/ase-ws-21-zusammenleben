package com.example.myapplication;

public class PaymentMemo {

    // All Data of the dataset. Pls add what ya need. this is probably not the complete list
    private String purpose;
    private String cost;
    private String email;

    public PaymentMemo (String cost, String purpose, String email) {
        this.cost = cost;
        this.purpose = purpose;
        this.email = email;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEmail(){return email;}

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setEmail(String email) {this.email = email;}

}



