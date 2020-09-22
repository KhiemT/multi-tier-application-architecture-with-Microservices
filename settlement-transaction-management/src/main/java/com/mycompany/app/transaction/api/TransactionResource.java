package com.mycompany.app.transaction.api;

import com.mycompany.app.transaction.rest.TransactionResourceImpl;
import com.mycompany.app.transaction.utils.Pagination;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mycompany.app.transaction.api.dto.TransactionDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;


public interface TransactionResource {

    /**
     * @api {POST} /api/transaction/add Add a new transaction.
     * @apiName addTransaction
     * @apiGroup transaction
     * @apiDescription Add a new transaction
     **@apiParamExample {json} Request-Example:
     * {
     *      "transactionCode": null,
     *      "companySymbol":"TSLA",
     *      "marketCode": "NY",
     *      "transactionType": "SELL",
     *      "stockValue": "@501",
     *      "commission": 100.5,
     *      "tax": 5.5,
     *      "client": "TESLA Technology",
     *      "createdOn":null
     * }
     * @apiSuccessExample Success-Response:
     *
     * HTTP/1.1 200 OK
     * {
     *   "transactionCode": "NYSE1100022",
     *   "companySymbol": "TSLA",
     *   "marketCode": "NY",
     *   "transactionType": "SELL",
     *   "stockValue": "@501",
     *   "commission": 100.5,
     *   "tax": 5.5,
     *   "client": "TESLA Technology",
     *   "createdOn": "2016-10-29 19:58:30"
     * }
     *
     *
     * @apiError TRANSACTION_ID_NOT_FOUND Could not find mapping for entity
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     *   "errorCode":"TRANSACTION_ID_NOT_FOUND",
     *   "errorMessage":"Transaction id is not generated"}
     * }
     *
     */
	ResponseEntity<Void> saveTransaction(TransactionDTO transactionDTO);

    Pagination<TransactionDTO> getTransactionList(Integer page, Integer pageSize);

    TransactionDTO findTransaction(String companySymbol,String transactionCode);


    void getDataSet() throws IOException, URISyntaxException;
}
