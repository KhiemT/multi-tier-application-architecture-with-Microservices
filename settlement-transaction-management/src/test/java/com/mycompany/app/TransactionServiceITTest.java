package com.mycompany.app;

import com.mycompany.app.transaction.Application;
import com.mycompany.app.transaction.api.dto.TransactionDTO;
import com.mycompany.app.transaction.domain.Transaction;
import com.mycompany.app.transaction.domain.TransactionComposeKey;
import com.mycompany.app.transaction.domain.TransactionType;
import com.mycompany.app.transaction.repository.TransactionRepository;
import com.mycompany.app.transaction.service.TransactionService;
import com.mycompany.app.transaction.utils.Pagination;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by Khiem on 10/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("devmock")
public class TransactionServiceITTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    private MockRestServiceServer mockServer;

    @Before
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        IntStream.range(1, 3).forEach(i -> {
            mockServer.expect(requestTo(
                            "http://localhost:8081/api/tidgenerator?symbol=TSLA"))
                    .andExpect(method(HttpMethod.GET)).andRespond(
                    withSuccess().contentType(MediaType.APPLICATION_JSON).body(
                            "{\"code\": \"NYSE110001" + i + "\"}"));
        });
    }

    @Test
    public void addTransactionSuccessTest() {
        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setCompanySymbol("TSLA");
            transactionDTO.setMarketCode("NY");
            transactionDTO.setTransactionType(TransactionType.SELL);
            transactionDTO.setStockValue("@501");
            transactionDTO.setCommission(100.5);
            transactionDTO.setTax(5.5);
            transactionDTO.setClient("TESLA Technology");
            transactionService.saveTransaction(transactionDTO);
            TransactionComposeKey key = new TransactionComposeKey("NYSE1100011","TSLA","NY");
            Transaction transaction = transactionRepository.findOne(key);
            Assert.assertNotNull(transaction);
            Assert.assertEquals("NYSE1100011", transaction.getTransactionCode());
            Assert.assertEquals("TSLA", transaction.getCompanySymbol());
            Assert.assertEquals("NY", transaction.getMarketCode());
            Assert.assertNotNull(transaction.getCreatedOn());

        } finally {
            transactionRepository.deleteAll();
        }
    }
    @Test
    public void getTransactionSuccessTest() {
        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setCompanySymbol("TSLA");
            transactionDTO.setMarketCode("NY");
            transactionDTO.setTransactionType(TransactionType.SELL);
            transactionDTO.setStockValue("@501");
            transactionDTO.setCommission(100.5);
            transactionDTO.setTax(5.5);
            transactionDTO.setClient("TESLA Technology");
            transactionService.saveTransaction(transactionDTO);
            TransactionDTO transactionDTO1 = transactionService.
                    findByTransactionCodeAndCompanySymbol("TSLA","NYSE1100011");
            Assert.assertEquals("NYSE1100011", transactionDTO1.getTransactionCode());
            Assert.assertEquals("TSLA", transactionDTO1.getCompanySymbol());
            Assert.assertEquals("NY", transactionDTO1.getMarketCode());
        } finally {
            transactionRepository.deleteAll();
        }
    }
    @Test
    public void getTransactionPaginationSuccessTest() {
        try {
            // Create a transaction SELL
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setCompanySymbol("TSLA");
            transactionDTO.setMarketCode("NY");
            transactionDTO.setTransactionType(TransactionType.SELL);
            transactionDTO.setStockValue("@501");
            transactionDTO.setCommission(100.5);
            transactionDTO.setTax(5.5);
            transactionDTO.setClient("TESLA Technology");
            transactionService.saveTransaction(transactionDTO);

            // Create a transaction BUY
            transactionDTO = new TransactionDTO();
            transactionDTO.setCompanySymbol("TSLA");
            transactionDTO.setMarketCode("NY");
            transactionDTO.setTransactionType(TransactionType.BUY);
            transactionDTO.setStockValue("@502");
            transactionDTO.setCommission(100.5);
            transactionDTO.setTax(5.4);
            transactionDTO.setClient("TESLA Technology");
            transactionService.saveTransaction(transactionDTO);

            Pagination<TransactionDTO> transactionPagination =  transactionService.getAllTransactionsByPaging(0,10);
            Assert.assertNotNull(transactionPagination.getContent());
            Assert.assertEquals("Should be page #0", 0L, transactionPagination.getPage());
            Assert.assertEquals("Should be one page", 1L, transactionPagination.getTotalPages());
            Assert.assertEquals("Found two transactions", 2, transactionPagination.getTotalResult());

        } finally {
            transactionRepository.deleteAll();
        }
    }
}
