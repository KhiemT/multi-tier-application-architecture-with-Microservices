package com.mycompany.app.transaction.api.dto;

/**
 * Created by Admin on 12/28/2016.
 */
public class DateClose {
    private String date;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
