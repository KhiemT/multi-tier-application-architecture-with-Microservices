package com.mycompany.app.transaction.gateway;

import com.mycompany.app.transaction.api.dto.StockDTO;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Admin on 12/28/2016.
 */
public interface StockDataGateway {
    StockDTO getDataSet(String tickerSymbol) throws URISyntaxException, IOException;
}
