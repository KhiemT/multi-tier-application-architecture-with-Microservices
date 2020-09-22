package com.mycompany.app.transaction.service;

import com.mycompany.app.transaction.api.dto.TransactionDTO;
import com.mycompany.app.transaction.utils.Pagination;

import java.io.IOException;
import java.net.URISyntaxException;

public interface TransactionService {
    void getDataSet() throws IOException, URISyntaxException;

    TransactionDTO saveTransaction(TransactionDTO transactionDTO);

    Pagination<TransactionDTO> getAllTransactionsByPaging(int page, int pageSize);

    TransactionDTO findByTransactionCodeAndCompanySymbol(String companySymbol, String transactionCode);
}
