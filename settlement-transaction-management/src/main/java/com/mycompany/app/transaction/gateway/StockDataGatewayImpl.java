package com.mycompany.app.transaction.gateway;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.transaction.api.dto.StockDTO;
import com.mycompany.app.transaction.api.dto.StockSvcDTO;
import com.mycompany.app.transaction.service.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.mycompany.app.transaction.utils.Constraint.JSON_TYPE;

/**
 * Created by Khiem on 12/30/2016.
 * Gateway endpoint for accessing stock data.
 */
@Service
public class StockDataGatewayImpl implements StockDataGateway {
    private final String stockDataEndpoint;
    private final StockMapper stockMapper;

    @Autowired
    public StockDataGatewayImpl(@Value("${gateway.endpoints.quandl.url}") String stockDataEndpoint,
                                StockMapper stockMapper) {
        this.stockMapper = stockMapper;
        this.stockDataEndpoint = stockDataEndpoint;
    }
    @Override
    public StockDTO getDataSet(String tickerSymbol) throws URISyntaxException, IOException {

        String url = stockDataEndpoint + tickerSymbol + JSON_TYPE;
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new URL(url));
        ObjectMapper objectMapper = new ObjectMapper();
        StockSvcDTO stock = objectMapper.readValue(parser, StockSvcDTO.class);
        return stockMapper.toStockDTO(stock);
    }
}
