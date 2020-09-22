package com.mycompany.app.transaction.api.dto;

/**
 * Created by Admin on 12/28/2016.
 * 200-day moving avergate.
 */
public class DayMovingAvgDTO {
    private String ticker;
    private Double avg;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
}
