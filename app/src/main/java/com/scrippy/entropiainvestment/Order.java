package com.scrippy.entropiainvestment;

public class Order {

    private Stock stockToBuyOrSell;
    private boolean isThisABuyOrder;
    private int amountOfStockToBuy;
    private double totalPrice;
    private boolean isCompleted;
    private String orderID;
    private boolean isCanceled;

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
}
