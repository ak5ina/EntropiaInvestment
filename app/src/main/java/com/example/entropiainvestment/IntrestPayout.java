package com.example.entropiainvestment;

public class IntrestPayout {

    private double buckspayout;
    private String date, info, payoutID, type, bitcoinAdress;

    public IntrestPayout(double buckspayout, String date, String info, String type){
        this.buckspayout = buckspayout;
        this.date = date;
        this.info = info;
        this.type = type;

    }

    public IntrestPayout(double buckspayout, String date, String info, String payoutID, String type){
        this.buckspayout = buckspayout;
        this.date = date;
        this.info = info;
        this.payoutID = payoutID;
        this.type = type;
    }

    public double getBuckspayout() {
        return buckspayout;
    }

    public void setBuckspayout(double buckspayout) {
        this.buckspayout = buckspayout;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPayoutID() {
        return payoutID;
    }

    public void setPayoutID(String payoutID) {
        this.payoutID = payoutID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBitcoinAdress() {
        return bitcoinAdress;
    }

    public void setBitcoinAdress(String bitcoinAdress) {
        this.bitcoinAdress = bitcoinAdress;
    }
}
