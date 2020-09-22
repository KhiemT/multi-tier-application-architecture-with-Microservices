package com.mycompany.app.transaction.service;

import com.mycompany.app.transaction.api.dto.TransactionDTO;
import com.mycompany.app.transaction.domain.Transaction;
import com.mycompany.app.transaction.gateway.IDGeneratorGateway;
import com.mycompany.app.transaction.gateway.StockDataGateway;
import com.mycompany.app.transaction.gateway.dto.IdCodeDTO;
import com.mycompany.app.transaction.repository.TransactionRepository;
import com.mycompany.app.transaction.service.mapper.TransactionMapper;
import com.mycompany.app.transaction.utils.Pagination;
import com.mycompany.app.transaction.exception.RestError;
import com.mycompany.app.transaction.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private IDGeneratorGateway idGeneratorGateway;

    @Autowired
    private StockDataGateway stockDataGateway;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void getDataSet() throws IOException, URISyntaxException {
        stockDataGateway.getDataSet("GE");
    }


    /**{@inheritDoc} **/
    @Override
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        IdCodeDTO idCode = idGeneratorGateway.getIdCode(transactionDTO.getCompanySymbol());
        if(StringUtils.isEmpty(idCode.getCode())) {
            throw new RestException(new RestError("TRANSACTION_ID_NOT_FOUND",
                    "Transaction id is not generated", HttpStatus.NOT_FOUND));
        }
        LOGGER.debug("Save a transaction id {} with symbol {} in marketCode {}",
                idCode.getCode(), transactionDTO.getCompanySymbol(),transactionDTO.getMarketCode());
        transactionDTO.setTransactionCode(idCode.getCode());
        Transaction transaction = transactionMapper.toTransaction(transactionDTO);
        return transactionMapper.toTransactionDTO(transactionRepository.save(transaction));
    }

    /**{@inheritDoc} **/
    @Override
    public TransactionDTO findByTransactionCodeAndCompanySymbol(String companySymbol, String transactionCode) {
        Transaction transaction = transactionRepository
                .findByTransactionCodeAndCompanySymbol(companySymbol, transactionCode)
                .orElseThrow(() -> new RestException(new RestError("TRANSACTION_NOT_FOUND",
                        "Transaction is not found", HttpStatus.NOT_FOUND)));
        return transactionMapper.toTransactionDTO(transaction);
    }

    /**{@inheritDoc} **/
    @Override
    public Pagination<TransactionDTO> getAllTransactionsByPaging(int page, int pageSize) {
        final PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(new Sort.Order(DESC, "createdOn")));
        Page<Transaction> transactionPage = transactionRepository.findAll(pageRequest);
        return convertToPagination(page, pageSize, transactionPage);
    }

    /** Convert current Page to customize Pagination
     */
    private Pagination<TransactionDTO> convertToPagination(int page, int pageSize, Page<Transaction> transactionPage) {
        Pagination<TransactionDTO> pagination  = new Pagination<>();
        pagination.setPage(page);
        Long total = transactionPage.getTotalElements();
        pagination.setTotalResult(total);
        if(total>0) {
            pagination.setTotalPages(total / pageSize + 1);
        }
        else{
            pagination.setTotalPages(0L);
        }
        pagination.getContent().addAll(transactionMapper.toTransactionDTOList(transactionPage.getContent()));
        return pagination;
    }

}
