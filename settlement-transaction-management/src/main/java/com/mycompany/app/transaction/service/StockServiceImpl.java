package com.mycompany.app.transaction.service;

import com.google.common.collect.ImmutableMap;
import com.mycompany.app.transaction.api.dto.*;
import com.mycompany.app.transaction.exception.CommonRestErrors;
import com.mycompany.app.transaction.exception.RestException;
import com.mycompany.app.transaction.gateway.StockDataGateway;
import com.mycompany.app.transaction.service.mapper.StockMapper;
import com.mycompany.app.transaction.utils.Constraint;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Created by Khiem on 12/30/2016.
 * Provide stock services for rest api.
 */
@Service
public class StockServiceImpl implements StockService {

    private static final int MOVING_DAYS = 200;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockDataGateway stockDataGateway;

    /**{@inheritDoc} **/
    @Override
    public ClosePrice getClosePrice(String tickerSymbol, String startDate, String endDate) {
        ClosePrice closePrice = new ClosePrice();
        // Check valid start date format first.
        checkValidDateTimeFormat(startDate, endDate);

        StockDTO stock = getStockCompanyData(tickerSymbol);

        DateTime start = DateTime.parse(startDate);
        DateTime end = DateTime.parse(endDate);

        List<DailyStockHistory> data = stock.getDataset().getDailyStockHistories();

        List<DailyStockHistory> filterList = data.stream()
                .filter(dailyStockHistory ->
                        dailyStockHistory.getDate().isAfter(start) &&
                                dailyStockHistory.getDate().isBefore(end)
                ).collect(Collectors.toList());

        List<String[]> dateCloses = new ArrayList<>();
        filterList.forEach(d -> {
            String[] dateClose = {d.getDate().toString(Constraint.DEFAULT_DATE_FORMAT), d.getClose().toString()};
            dateCloses.add(dateClose);
        });

        Price price = new Price();
        price.setTicker(stock.getDataset().getDatasetCode());
        price.setDateClose(dateCloses);

        closePrice.setPrice(price);

        return closePrice;
    }
    /**{@inheritDoc} **/
    @Override
    public TwoHundredDmaDTO calculate200DayMovingAvg(String tickerSymbol, String startDate) {
        TwoHundredDmaDTO twoHundredDmaDTO = new TwoHundredDmaDTO();

        // Check valid start date format first.
        checkValidDateTimeFormat(startDate);

        // Next, get stock data from gateway endpoint.
        StockDTO stock = getStockCompanyData(tickerSymbol);

        DateTime end = DateTime.parse(startDate);
        DateTime start = end.minusDays(MOVING_DAYS);
        // Check start date with NewestAvailableDate
        checkInValidStartDate(stock, end);

        List<DailyStockHistory> data = stock.getDataset().getDailyStockHistories();

        // Filter stock history basing on date time.
        List<DailyStockHistory> stockHistoriesFilter = data.stream()
                .filter(dailyStockHistory ->
                        dailyStockHistory.getDate().isAfter(start) &&
                                dailyStockHistory.getDate().isBefore(end)
                ).collect(Collectors.toList());
        // Calculate avg from stock history found.
        OptionalDouble avg = stockHistoriesFilter.stream().mapToDouble(DailyStockHistory::getClose).average();

        // Create a DayMovingAvgDTO.
        DayMovingAvgDTO dayMovingAvgDTO = new DayMovingAvgDTO();
        dayMovingAvgDTO.setAvg(avg.orElse(0D));
        dayMovingAvgDTO.setTicker(stock.getDataset().getDatasetCode());
        twoHundredDmaDTO.setDma(dayMovingAvgDTO);

       return twoHundredDmaDTO;
    }

    private void checkValidDateTimeFormat(String ... dates) {
        try {
            for (String date : dates){
                DateTime.parse(date);
            }
        } catch (IllegalArgumentException e){
            throw new RestException(CommonRestErrors.INVALID_DATE_TIME, ImmutableMap.<String, Object>builder()
                    .put("DateTime format", "Please check:{" + Arrays.toString(dates) + "}").build());
        }

    }

    private void checkInValidStartDate(StockDTO stock, DateTime dateTime) {
        String newestAvailableDay = stock.getDataset().getNewestAvailableDate();
        if(dateTime.isAfter(DateTime.parse(stock.getDataset().getNewestAvailableDate()))){
            throw new RestException(CommonRestErrors.START_DATE_DATA_NOT_FOUND, ImmutableMap.<String, Object>builder()
                    .put("Newest Available Date", "the first possible start" +
                            "date is suggested:{" + newestAvailableDay + "}").build());
        }
    }
    private StockDTO getStockCompanyData(String tickerSymbol) {
        StockDTO stock;
        try {
            stock = stockDataGateway.getDataSet(tickerSymbol);
        } catch (IOException | URISyntaxException e) {
            throw new RestException(CommonRestErrors.STOCK_NOT_FOUND, ImmutableMap.<String, Object> builder()
                    .put("Company stock symbol", "{" +tickerSymbol +"} was not found.").build());
        }
        return stock;
    }
}
