package com.example.myapplication.entities;

public class PaymentMemo {

    // All Data of the dataset. Pls add what ya need. this is probably not the complete list
    private String purpose;
    private double cost;
    private String email;
    //private String flatsize;

    public PaymentMemo (double cost, String purpose, String email) {
        this.cost = cost;
        this.purpose = purpose;
        this.email = email;
        //this.flatsize = flatsize
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEmail(){return email;}

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setEmail(String email) {this.email = email;}

    //public String getFlatSize () {return flatsize;}

    //public void setFlatSize (String flatsize) {this.flatsize = flatsize;}

}



