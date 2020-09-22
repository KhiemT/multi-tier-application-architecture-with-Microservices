package com.mycompany.app.transaction.api.dto;

import java.util.List;

/**
 * Created by Khiem on 12/28/2016.
 */
public class Price {
    private String ticker;
    private List<String[]> dateClose;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<String[]> getDateClose() {
        return dateClose;
    }

    public void setDateClose(List<String[]> dateClose) {
        this.dateClose = dateClose;
    }
}
