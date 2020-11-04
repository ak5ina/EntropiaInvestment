package com.example.entropiainvestment;

public class Stock {

    private double value;
    private String name;
    private String info;
    private int amountOwned;
    private double combinedValueOfAllOwnedStock;

        public Stock(double value, String name, String info){

        this.value = value;
        this.name = name;
        this.info = info;

        }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public int getAmountOwned() {
        return amountOwned;
    }

    public void setAmountOwned(int amountOwned) {
        this.amountOwned = amountOwned;
    }

    public double getCombinedValueOfAllOwnedStock() {
        return combinedValueOfAllOwnedStock;
    }

    public void setCombinedValueOfAllOwnedStock(double combinedValueOfAllOwnedStock) {
        this.combinedValueOfAllOwnedStock = combinedValueOfAllOwnedStock;
    }
}