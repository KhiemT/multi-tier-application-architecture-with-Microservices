package com.mycompany.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.transaction.Application;
import com.mycompany.app.transaction.api.dto.DailyStockHistory;
import com.mycompany.app.transaction.api.dto.StockDTO;
import com.mycompany.app.transaction.api.dto.StockSvcDTO;
import com.mycompany.app.transaction.gateway.StockDataGateway;
import com.mycompany.app.transaction.service.mapper.StockMapper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Khiem on 12/30/2016.
 * Test for StockDateGateway.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("devmock")
public class StockDateGatewayITest {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockDataGateway stockDataGateway;

    private StockDTO stockDTO;
    @Before
    public void setup() throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL resource = StockDateGatewayITest.class.getClassLoader().getResource("GE.json");
        StockSvcDTO stock = objectMapper.readValue(Paths.get(resource.toURI()).toFile(), StockSvcDTO.class);
        stockDTO = stockMapper.toStockDTO(stock);
    }
    @Test
    public void testGetDataSet() throws IOException, URISyntaxException {
        StockDTO stockDTOGateway = stockDataGateway.getDataSet("GE");
        Assert.assertNotNull(stockDTOGateway);
        Assert.assertNotNull(stockDTOGateway.getDataset());
        Assert.assertEquals(stockDTOGateway.getDataset().getDatasetCode(),
                stockDTO.getDataset().getDatasetCode());
        Assert.assertEquals(stockDTOGateway.getDataset().getId(),
                stockDTO.getDataset().getId());
        Assert.assertEquals(stockDTOGateway.getDataset().getFrequency(),"daily");

        Assert.assertNotNull(stockDTOGateway.getDataset().getDailyStockHistories());
        List<DailyStockHistory> dailyStockHistoryList = stockDTOGateway.getDataset().getDailyStockHistories();

        DailyStockHistory source = dailyStockHistoryList.stream()
                .filter(s -> s.getDate().equals(DateTime.parse("2016-12-28"))).findFirst().orElse(null);
        Assert.assertNotNull(source);

        DailyStockHistory dest = stockDTO.getDataset().getDailyStockHistories().stream()
                .filter(s -> s.getDate().equals(DateTime.parse("2016-12-28"))).findFirst().orElse(null);
        Assert.assertNotNull(dest);

        Assert.assertEquals(source.getDate(), dest.getDate());
        Assert.assertEquals(source.getClose(), dest.getClose());
        Assert.assertEquals(source.getHigh(), dest.getHigh());
        Assert.assertEquals(source.getLow(), dest.getLow());
        Assert.assertEquals(source.getOpen(), dest.getOpen());
        Assert.assertEquals(source.getVolume(), dest.getAdjVolume());
        Assert.assertEquals(source.getAdjOpen(), dest.getAdjOpen());
        Assert.assertEquals(source.getAdjClose(), dest.getAdjClose());
        Assert.assertEquals(source.getAdjHigh(), dest.getAdjHigh());
    }

}
