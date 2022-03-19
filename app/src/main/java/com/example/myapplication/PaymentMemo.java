package com.example.myapplication;

import java.util.ArrayList;

public class PaymentMemo {

    // All Data of the dataset. Pls add what ya need. this is probably not the complete list
    private String purpose;
    private double cost;
    private String email;

    private String receiverName;
    private String flat;

    public PaymentMemo (){

    }

    public PaymentMemo (double cost, String purpose, String email, String receiverName, String flat) {
        this.cost = cost;
        this.purpose = purpose;
        this.email = email;

        this.receiverName = receiverName;
        this.flat = flat;

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


    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName){
        this.receiverName = receiverName;
    }

    public String getFlat(){
        return flat;
    }

    public void setFlat(){
        this.flat = flat;
    }

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add(purpose);
        data.add(String.valueOf(cost));
        data.add(email);
        data.add(receiverName);
        data.add(flat);

        return data;
    }
}



