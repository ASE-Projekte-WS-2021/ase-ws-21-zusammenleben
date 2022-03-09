package com.example.myapplication;

public class Flats {

    String firstUser, secondUser, thirdUser, fourthUser, fifthUser, address, name, profileName;
    int flatSize;

    public Flats(){

    }
    public Flats(String firstUser, String secondUser, String address, int flatSize, String name, String profileName){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
        this.profileName = profileName;
    }
    public Flats(String firstUser, String secondUser, String thirdUser, String address, int flatSize, String name, String profileName){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
        this.profileName = profileName;

    }
    public Flats(String firstUser, String secondUser, String thirdUser, String fourthUser, String address, int flatSize, String name, String profileName){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.fourthUser = fourthUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
        this.profileName = profileName;

    }
    public Flats(String firstUser, String secondUser, String thirdUser, String fourthUser, String fifthUser, String address, int flatSize, String name, String profileName) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
        this.fourthUser = fourthUser;
        this.fifthUser = fifthUser;
        this.address = address;
        this.flatSize = flatSize;
        this.name = name;
        this.profileName = profileName;

    }

    public void setAddress() {
        this.address = address;
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

    public int getFlatSize() {
        return flatSize;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getName(){
        return name;
    }

}
