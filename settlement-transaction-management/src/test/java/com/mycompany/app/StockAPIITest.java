package com.mycompany.app;

import com.mycompany.app.transaction.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Khiem on 12/30/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class StockAPIITest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);

    }

    @Test
    public void testClosePriceAPISuccessResponse() throws Exception{
        mockMvc.perform(get("http://localhost:8080/api/v2/GE/closePrice?startDate=2016-07-08&endDate=2016-12-28"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.price.ticker", is("GE")))
                .andExpect(jsonPath("$.price.dateClose.[0].[0]", is("2016-12-27")))
                .andExpect(jsonPath("$.price.dateClose.[0].[1]", is("31.9")));
    }
    @Test
    public void testClosePriceAPIErrorResponseStockNotFound() throws Exception{
        mockMvc.perform(get("http://localhost:8080/api/v2/UNKNOWN_TICKER_SYMBOL/closePrice?startDate=2016-07-08&endDate=2016-12-28"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.errorCode", is("STOCK_NOT_FOUND")))
                .andExpect(jsonPath("$.errorMessage", is("Stock is not found")))
                .andExpect(jsonPath("$.params.[*]").value("{UNKNOWN_TICKER_SYMBOL} was not found."));
    }

    @Test
    public void testClosePriceAPIErrorResponseInValidDateTime() throws Exception{
        mockMvc.perform(get("http://localhost:8080/api/v2/GE/closePrice?startDate=INVALID_START_DATE&endDate=2016-12-28"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.errorCode", is("INVALID_DATE_TIME")))
                .andExpect(jsonPath("$.errorMessage", is("Date time format is invalid")))
                .andExpect(jsonPath("$.params.[*]").value("Please check:{[INVALID_START_DATE, 2016-12-28]}"));
    }
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}
