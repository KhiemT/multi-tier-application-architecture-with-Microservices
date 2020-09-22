package com.mycompany.app.transaction.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 12/28/2016.
 */
public class TwoHundredDmaDTO {
    @JsonProperty("200dma")
    private DayMovingAvgDTO dma;

    public DayMovingAvgDTO getDma() {
        return dma;
    }

    public void setDma(DayMovingAvgDTO dma) {
        this.dma = dma;
    }
}
