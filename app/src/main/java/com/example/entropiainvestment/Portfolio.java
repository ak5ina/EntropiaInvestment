package com.example.entropiainvestment;

import java.util.ArrayList;

public class Portfolio {

    private ArrayList<Integer> stockYouOwn;

    public Portfolio(ArrayList<Integer> stockYouOwn){
        this.stockYouOwn = stockYouOwn;
    }

    public Portfolio(){
        stockYouOwn = new ArrayList<>();
    }

    public ArrayList<Integer> getStockYouOwn() {
        return stockYouOwn;
    }

    public void setStockYouOwn(ArrayList<Integer> stockYouOwn) {
        this.stockYouOwn = stockYouOwn;
    }
}
