package com.mycompany.app.transaction.gateway;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.transaction.api.dto.*;
import com.mycompany.app.transaction.api.dto.DatasetSvcDTO;
import com.mycompany.app.transaction.gateway.dto.IdCodeDTO;
import com.mycompany.app.transaction.service.mapper.StockMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class IDGeneratorGatewayImpl implements IDGeneratorGateway {
	private final RestTemplate restTemplate;
    private final String tidGeneratorEndpoint;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    public IDGeneratorGatewayImpl(@Value("${gateway.endpoints.idGenerator.url}") String tidGeneratorEndpoint,
                                  RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.tidGeneratorEndpoint = tidGeneratorEndpoint;
    }

    @Override
    public IdCodeDTO getIdCode(String symbol) {
        URI url = UriComponentsBuilder.fromHttpUrl(tidGeneratorEndpoint).queryParam("symbol", symbol).build().toUri();
        return restTemplate.getForObject(url, IdCodeDTO.class);
    }
    @Override
    public DatasetSvcDTO getDataset() throws URISyntaxException, IOException {
       /* Gson gson = new Gson();
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/GE.json";
        DatasetSvcDTO dataset = gson.fromJson(IOUtils.toString(new URL(url)), DatasetSvcDTO.class);*/

        String url = "https://www.quandl.com/api/v3/datasets/WIKI/GE.json";
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new URL(url));
        ObjectMapper objectMapper = new ObjectMapper();
        StockSvcDTO stock = objectMapper.readValue(parser, StockSvcDTO.class);

        StockDTO stockDTO = stockMapper.toStockDTO(stock);
        //caculate200DayMovingAvg(stockDTO, "");
        //getClosePrice(stockDTO,"","");
        /*JsonNode responseJson = objectMapper.readTree(new URL(url));
        DatasetSvcDTO dataset = objectMapper.readValue(responseJson.traverse().getValueAsString("dataset"), DatasetSvcDTO.class);*/
        return null;
    }

    /*private DayMovingAvgDTO caculate200DayMovingAvg(StockDTO stock, String startDate) {
        startDate = "2016-12-20";
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime end = DateTime.parse(startDate,fmt);

        DateTime start = end.minusDays(200);

        List<DailyStockHistory> data = stock.getDataset().getDailyStockHistories();

        List<DailyStockHistory> stockHistoriesFilter = data.stream()
                .filter(dailyStockHistory ->
                        dailyStockHistory.getDate().isAfter(start) &&
                                dailyStockHistory.getDate().isBefore(end)
                ).collect(Collectors.toList());
        OptionalDouble avg = stockHistoriesFilter.stream().mapToDouble(DailyStockHistory::getClose).average();
        DayMovingAvgDTO dayMovingAvgDTO = new DayMovingAvgDTO();
        dayMovingAvgDTO.setAvg(avg.orElse(0D));
        dayMovingAvgDTO.setTicker(stock.getDataset().getDatasetCode());
        return dayMovingAvgDTO;
    }*/


   /* private ClosePrice getClosePrice(StockDTO stock, String startDate, String endDate) {
        startDate = "2016-12-20";
        endDate = "2016-12-23";
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime start = DateTime.parse(startDate,fmt);
        DateTime end = DateTime.parse(endDate,fmt);

        Price price = new Price();
        price.setTicker(stock.getDataset().getDatasetCode());

        List<DailyStockHistory> data = stock.getDataset().getDailyStockHistories();

        List<DailyStockHistory> stockHistoriesFilter = data.stream()
                .filter(dailyStockHistory ->
                        dailyStockHistory.getDate().isAfter(start) &&
                        dailyStockHistory.getDate().isBefore(end)
                ).collect(Collectors.toList());
        List<DateClose> dateCloses = stockMapper.toDateCloseList(stockHistoriesFilter);
        price.setDateClose(dateCloses);
        ClosePrice closePrice = new ClosePrice();
        closePrice.setPrice(price);
        return closePrice;
    }*/
}
