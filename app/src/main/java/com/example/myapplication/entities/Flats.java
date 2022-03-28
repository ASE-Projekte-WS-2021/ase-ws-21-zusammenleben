package com.example.myapplication.entities;

import java.util.ArrayList;

import java.util.ArrayList;

public class Flats {

    String firstUser, secondUser, thirdUser, fourthUser, fifthUser, name, flatID;
    int flatSize;

    public Flats(){
    }

    // If the Flat has two members
    public Flats(String firstUser, String secondUser, int flatSize, String name, String flatID){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.flatSize = flatSize;
        this.name = name;
        this.flatID = flatID;
    }

    // If the Flat has three members
    public Flats(String firstUser, String secondUser, String thirdUser, int flatSize, String name, String flatID){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.flatSize = flatSize;
        this.name = name;
        this.flatID = flatID;
    }

    // If the flat has four members
    public Flats(String firstUser, String secondUser, String thirdUser, String fourthUser, int flatSize, String name, String flatID){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.fourthUser = fourthUser;
        this.flatSize = flatSize;
        this.name = name;
        this.flatID = flatID;
    }

    // If the flat has five members
    public Flats(String firstUser, String secondUser, String thirdUser, String fourthUser, String fifthUser, int flatSize, String name, String flatID) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.fourthUser = fourthUser;
        this.fifthUser = fifthUser;
        this.flatSize = flatSize;
        this.name = name;
        this.flatID = flatID;

    }

    public String getFirstUser() {
        return firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public String getThirdUser() {
        return thirdUser;
    }

    public String getFourthUser() {
        return fourthUser;
    }

    public String getFifthUser() {
        return fifthUser;
    }

    public int getFlatSize() {
        return flatSize;
    }

    public String getFlatID() {
        return flatID;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getData(int size){
        ArrayList<String> data = new ArrayList<String>();

        if(size == 2){
            data.add(flatID);
            data.add(firstUser);
            data.add(secondUser);
        }

        if(size == 3){
            data.add(flatID);
            data.add(firstUser);
            data.add(secondUser);
            data.add(thirdUser);
        }

        if(size == 4){
            data.add(flatID);
            data.add(firstUser);
            data.add(secondUser);
            data.add(thirdUser);
            data.add(fourthUser);
        }

        if(size == 5){
            data.add(flatID);
            data.add(firstUser);
            data.add(secondUser);
            data.add(thirdUser);
            data.add(fourthUser);
            data.add(fifthUser);
        }
        return data;
    }
}
