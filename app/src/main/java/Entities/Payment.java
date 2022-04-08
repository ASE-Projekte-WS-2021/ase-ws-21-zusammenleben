package Entities;

import java.util.ArrayList;

public class Payment {

    double cost;
    String purpose, creator, flatID, paymentID;
    ArrayList<String> receiver;

    public Payment(double cost, String purpose, String creator, ArrayList<String> receiver, String flatID, String paymentID){
        this.cost = cost;
        this.purpose = purpose;
        this.creator = creator;
        this.receiver = receiver;
        this.flatID = flatID;
        this.paymentID = paymentID;
    }

    public Payment() {}

    public double getCost(){
        return cost;
    }

    public String getPurpose(){
        return purpose;
    }

    public String getCreator(){
        return creator;
    }

    public ArrayList<String> getReceiver(){
        return receiver;
    }

    public String getFlatID(){
        return flatID;
    }

    public String getPaymentID(){
        return paymentID;
    }

    public void setFlatID(String id){
        this.flatID = id;
    }

    public void setPaymentID(String id){
        this.paymentID = id;
    }
}
