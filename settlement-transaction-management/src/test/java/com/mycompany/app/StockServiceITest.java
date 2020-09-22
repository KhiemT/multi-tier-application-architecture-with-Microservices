package com.mycompany.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.transaction.Application;
import com.mycompany.app.transaction.api.dto.ClosePrice;
import com.mycompany.app.transaction.api.dto.StockDTO;
import com.mycompany.app.transaction.api.dto.StockSvcDTO;
import com.mycompany.app.transaction.api.dto.TwoHundredDmaDTO;
import com.mycompany.app.transaction.exception.RestException;
import com.mycompany.app.transaction.gateway.StockDataGateway;
import com.mycompany.app.transaction.service.StockServiceImpl;
import com.mycompany.app.transaction.service.mapper.StockMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Khiem on 12/30/2016.
 * Test Stock Services.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("devmock")
public class StockServiceITest {

    @InjectMocks
    private StockMapper stockMapper = new StockMapper();

    @InjectMocks
    private StockServiceImpl stockServiceImpl = new StockServiceImpl();

    @Mock
    private StockDataGateway stockDataGateway;

    @Before
    public void setup() throws IOException, URISyntaxException {
        initMocks(this);

        when(stockDataGateway.getDataSet("GE")).thenAnswer(new Answer<StockDTO>() {
            @Override
            public StockDTO answer(InvocationOnMock invocationOnMock) throws Throwable {
                ObjectMapper objectMapper = new ObjectMapper();
                URL geResource = StockDateGatewayITest.class.getClassLoader().getResource("GE.json");
                StockSvcDTO stock = objectMapper.readValue(Paths.get(geResource.toURI()).toFile(), StockSvcDTO.class);
                return stockMapper.toStockDTO(stock);
            }
        });
    }

    @Test
    public void testGetClosePriceSuccess() throws IOException, URISyntaxException {

        ObjectMapper objectMapper = new ObjectMapper();
        URL closePriceResource = StockDateGatewayITest.class.getClassLoader().getResource("ClosePrice.json");
        ClosePrice expected = objectMapper.readValue(Paths.get(closePriceResource.toURI()).toFile(), ClosePrice.class);

        ClosePrice actual = stockServiceImpl.getClosePrice("GE","2016-07-08", "2016-12-28");
        Assert.assertEquals(expected.getPrice().getTicker(), actual.getPrice().getTicker());
        Assert.assertEquals(expected.getPrice().getDateClose().size(), actual.getPrice().getDateClose().size());
        Assert.assertEquals(expected.getPrice().getDateClose().get(0)[0], actual.getPrice().getDateClose().get(0)[0]);
        Assert.assertEquals(expected.getPrice().getDateClose().get(0)[1], actual.getPrice().getDateClose().get(0)[1]);
    }

    @Test(expected = RestException.class)
    public void testGetClosePriceException() throws IOException, URISyntaxException {
        stockServiceImpl.getClosePrice("GE", "2016-07-08", "2016-12-280");
    }

    @Test
    public void testCalculate200DayMovingAvgSuccess() {
        TwoHundredDmaDTO actual = stockServiceImpl.calculate200DayMovingAvg("GE","2016-12-28");
        Assert.assertEquals("GE", actual.getDma().getTicker());
        Assert.assertEquals(Double.valueOf(31.766666666666666), actual.getDma().getAvg(),2);
    }

    @Test(expected = RestException.class)
    public void testCalculate200DayMovingAvgException() {
        stockServiceImpl.calculate200DayMovingAvg("GE","2017-12-28");
    }

}
