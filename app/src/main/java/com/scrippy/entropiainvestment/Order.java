package com.scrippy.entropiainvestment;

import java.security.PublicKey;

public class Order {

    private Stock stockToBuyOrSell;
    private boolean isThisABuyOrder;
    private int amountOfStockToBuy;
    private double totalPrice;
    private boolean isCompleted;
    private String orderID, stockToBuyOrSell1, stockID;
    private boolean isCanceled;
    private User user;

    public Order(Stock stockToBuyOrSell, boolean isThisABuyOrder, int amountOfStockToBuy, double totalPrice){

        this.stockToBuyOrSell = stockToBuyOrSell;
        this.isThisABuyOrder = isThisABuyOrder;
        this.amountOfStockToBuy = amountOfStockToBuy;
        this.totalPrice = totalPrice;
        this.isCompleted = false;
        this.isCanceled = false;

    }

    public Order(Stock stockToBuyOrSell, boolean isThisABuyOrder, int amountOfStockToBuy, double totalPrice, boolean isCompleted, String orderID, boolean isCanceled) {


        this.stockToBuyOrSell = stockToBuyOrSell;
        this.isThisABuyOrder = isThisABuyOrder;
        this.amountOfStockToBuy = amountOfStockToBuy;
        this.totalPrice = totalPrice;
        this.isCompleted = isCompleted;
        this.orderID = orderID;
        this.isCanceled = isCanceled;

    }

    public Order(String stockToBuyOrSell1, boolean isThisABuyOrder, int amountOfStockToBuy, double totalPrice, boolean isCompleted, String orderID, boolean isCanceled, User userID, String stockID) {


        this.stockToBuyOrSell1 = stockToBuyOrSell1;
        this.isThisABuyOrder = isThisABuyOrder;
        this.amountOfStockToBuy = amountOfStockToBuy;
        this.totalPrice = totalPrice;
        this.isCompleted = isCompleted;
        this.orderID = orderID;
        this.isCanceled = isCanceled;
        this.user = userID;
        this.stockID = stockID;

    }


    public Stock getStockToBuyOrSell() {
        return stockToBuyOrSell;
    }

    public boolean isThisABuyOrder() {
        return isThisABuyOrder;
    }

    public int getAmountOfStockToBuy() {
        return amountOfStockToBuy;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getStockToBuyOrSell1() {
        return stockToBuyOrSell1;
    }

    public void setStockToBuyOrSell1(String stockToBuyOrSell1) {
        this.stockToBuyOrSell1 = stockToBuyOrSell1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }
}
