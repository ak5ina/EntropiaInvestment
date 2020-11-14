package com.scrippy.entropiainvestment;

import java.util.ArrayList;

public class User {

    private String userEmail;
    private double bank;
    private Portfolio portfolio;
    private ArrayList<IntrestPayout> intrestPayouts;
    private ArrayList<Order> myOrders;
    private String authUID;

    public User(String userEmail, double bank){
        this.userEmail = userEmail;
        this.bank = bank;
        intrestPayouts = new ArrayList<>();
        portfolio = new Portfolio();
        myOrders = new ArrayList<>();
    }

    public User(String userEmail, double bank, String authUID){
        this.userEmail = userEmail;
        this.bank = bank;
        intrestPayouts = new ArrayList<>();
        portfolio = new Portfolio();
        myOrders = new ArrayList<>();
        this.authUID = authUID;
    }

    public User(){
        this.userEmail = "Unknown";
        this.bank = 0;
        intrestPayouts = new ArrayList<>();
        portfolio = new Portfolio();
        myOrders = new ArrayList<>();
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getBank() {
        return bank;
    }

    public void setBank(double bank) {
        this.bank = bank;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public ArrayList<IntrestPayout> getIntrestPayouts() {
        return intrestPayouts;
    }

    public void setIntrestPayouts(ArrayList<IntrestPayout> intrestPayouts) {
        this.intrestPayouts = intrestPayouts;
    }


    public ArrayList<Order> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(ArrayList<Order> myOrders) {
        this.myOrders = myOrders;
    }

    public String getAuthUID() {
        return authUID;
    }

    public void setAuthUID(String authUID) {
        this.authUID = authUID;
    }
}
