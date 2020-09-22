package com.mycompany.app.transaction.api.dto;


import org.joda.time.DateTime;

/**
 * Created by Admin on 12/28/2016.
 */
public class DailyStockHistory {
    private DateTime date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private String volume;
    private Double exDividend;
    private Double splitRatio;
    private Double adjOpen;
    private Double adjHigh;
    private Double adjLow;
    private Double adjClose;
    private String adjVolume;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }


    public Double getExDividend() {
        return exDividend;
    }

    public void setExDividend(Double exDividend) {
        this.exDividend = exDividend;
    }

    public Double getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(Double splitRatio) {
        this.splitRatio = splitRatio;
    }

    public Double getAdjOpen() {
        return adjOpen;
    }

    public void setAdjOpen(Double adjOpen) {
        this.adjOpen = adjOpen;
    }

    public Double getAdjHigh() {
        return adjHigh;
    }

    public void setAdjHigh(Double adjHigh) {
        this.adjHigh = adjHigh;
    }

    public Double getAdjLow() {
        return adjLow;
    }

    public void setAdjLow(Double adjLow) {
        this.adjLow = adjLow;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAdjVolume() {
        return adjVolume;
    }

    public void setAdjVolume(String adjVolume) {
        this.adjVolume = adjVolume;
    }
}
