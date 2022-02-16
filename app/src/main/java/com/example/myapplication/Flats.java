package com.example.myapplication;

public class Flats {

    String firstUser, secondUser, thirdUser, fourthUser, fifthUser, address, flatSize, name;

    public Flats(String firstUser, String secondUser, String address, String flatSize, String name){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
    }
    public Flats(String firstUser, String secondUser, String thirdUser, String address, String flatSize, String name){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
    }
    public Flats(String firstUser, String secondUser, String thirdUser, String fourthUser, String address, String flatSize, String name){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.fourthUser = fourthUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
    }
    public Flats(String firstUser, String secondUser, String thirdUser, String fourthUser, String fifthUser, String address, String flatSize, String name) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.fourthUser = fourthUser;
        this.fifthUser = fifthUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public String getFlatSize() {
        return flatSize;
    }

    public String getName(){
        return name;
    }

}
