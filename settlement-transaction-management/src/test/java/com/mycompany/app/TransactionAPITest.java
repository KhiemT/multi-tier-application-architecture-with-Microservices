package com.mycompany.app;

import com.mycompany.app.transaction.Application;
import com.mycompany.app.transaction.api.dto.TransactionDTO;
import com.mycompany.app.transaction.domain.TransactionType;
import com.mycompany.app.transaction.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Khiem on 10/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TransactionAPITest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    @Before
    public void setup() throws Exception {

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        IntStream.range(1, 3).forEach(i -> {
            mockServer.expect(requestTo(
                    "http://localhost:8081/api/tidgenerator?symbol=TSLA"))
                    .andExpect(method(HttpMethod.GET)).andRespond(
                    withSuccess().contentType(MediaType.APPLICATION_JSON).body(
                            "{\"code\": \"NYSE110001" + i + "\"}"));
        });

        initiateData();
    }



    @Test
    public void createTransaction() throws Exception {
        // Client SELL @501 TSLA
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCompanySymbol("TSLA");
        transactionDTO.setMarketCode("NY");
        transactionDTO.setTransactionType(TransactionType.SELL);
        transactionDTO.setStockValue("@501");
        transactionDTO.setCommission(100.5);
        transactionDTO.setTax(5.5);
        transactionDTO.setClient("TESLA Technology");

        mockMvc.perform(post("http://localhost:8082/api/transaction/add")
                .content(this.json(transactionDTO))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void readSingleTransaction() throws Exception {
        mockMvc.perform(get("http://localhost:8082/api/transaction/TSLA/NYSE1100012"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.transactionCode", is("NYSE1100012")))
                .andExpect(jsonPath("$.companySymbol", is("TSLA")))
                .andExpect(jsonPath("$.marketCode", is("NY")))
                .andExpect(jsonPath("$.transactionType", is("SELL")))
                .andExpect(jsonPath("$.stockValue", is("@501")))
                .andExpect(jsonPath("$.commission", is(100.5)))
                .andExpect(jsonPath("$.tax", is(5.5)))
                .andExpect(jsonPath("$.client", is("TESLA Technology")));

    }

    @Test
    public void getTransactionlist() throws Exception {
        // Execute API to get list of transactions.
        String url = "http://localhost:8082/api/transaction/list?page=0&pageSize=10";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[0].transactionCode", is("NYSE1100012")))
                .andExpect(jsonPath("$.content.[0].companySymbol", is("TSLA")))
                .andExpect(jsonPath("$.content.[0].marketCode", is("NY")))
                .andExpect(jsonPath("$.content.[0].transactionType", is("SELL")))
                .andExpect(jsonPath("$.content.[0].commission", is(100.5)))
                .andExpect(jsonPath("$.content.[1].transactionCode", is("NYSE1100011")))
                .andExpect(jsonPath("$.content.[1].companySymbol", is("TSLA")))
                .andExpect(jsonPath("$.content.[1].marketCode", is("NY")))
                .andExpect(jsonPath("$.content.[1].transactionType", is("BUY")))
                .andExpect(jsonPath("$.content.[1].commission", is(100.4)));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    private void initiateData() throws Exception{
        // Client BUY @501 TSLA
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCompanySymbol("TSLA");
        transactionDTO.setMarketCode("NY");
        transactionDTO.setTransactionType(TransactionType.BUY);
        transactionDTO.setStockValue("@501");
        transactionDTO.setCommission(100.4);
        transactionDTO.setTax(5.3);
        transactionDTO.setClient("TESLA Technology");
        transactionService.saveTransaction(transactionDTO);
    }

}
