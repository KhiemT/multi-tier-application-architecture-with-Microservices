package com.mycompany.app.transaction.service;

import com.mycompany.app.transaction.api.dto.ClosePrice;
import com.mycompany.app.transaction.api.dto.DayMovingAvgDTO;
import com.mycompany.app.transaction.api.dto.StockDTO;
import com.mycompany.app.transaction.api.dto.TwoHundredDmaDTO;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Khiem on 12/28/2016.
 */
public interface StockService {
    /**
     * Get close price of stock company symbol from arrange date.
     * @param tickerSymbol stock company symbol.
     * @param startDate a day from
     * @param endDate a day to
     * @return ClosePrice
     */
    ClosePrice getClosePrice(String tickerSymbol, String startDate, String endDate);

    /**
     * Calculate 200 days moving avg.
     * @param tickerSymbol stock company symbol.
     * @param startDate a day to end.
     * @return DayMovingAvgDTO
     */
    TwoHundredDmaDTO calculate200DayMovingAvg(String tickerSymbol, String startDate);

}
