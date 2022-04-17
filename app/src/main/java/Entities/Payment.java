package Entities;

import java.util.ArrayList;

public class Payment {

    String purpose, creator, flatID, paymentID, receiverString;
    ArrayList<String> receiver;
    double cost;

    // This constructor is used for the normal payment
    public Payment(double cost, String purpose, String creator, ArrayList<String> receiver, String flatID, String paymentID){
        this.cost = cost;
        this.purpose = purpose;
        this.creator = creator;
        this.receiver = receiver;
        this.flatID = flatID;
        this.paymentID = paymentID;
    }

    // This constructor is only used to calculate the sum inside OverviewPresenter
    // It is easier to create a simpler object than to extract specific data from the Firebase
    public Payment(double cost, String receiverString){
        this.cost = cost;
        this.receiverString = receiverString;
    }

    // This method is only used in OverviewPresenter
    public String getReceiverString(){
        return receiverString;
    }

    // default empty constructor (required when working with Firebase)
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
