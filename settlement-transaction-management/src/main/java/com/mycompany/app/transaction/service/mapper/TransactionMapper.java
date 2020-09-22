package com.mycompany.app.transaction.service.mapper;


import com.mycompany.app.transaction.api.dto.TransactionDTO;
import com.mycompany.app.transaction.domain.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class TransactionMapper {
    public ModelMapper modelMapper;

    public TransactionMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new ToTransactionMapping());
        modelMapper.addMappings(new ToTransactionDTOMapping());
    }

    /** Mapping from TransactionDTO to Transaction
     */
    public Transaction toTransaction(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);
    }
    /** Mapping from Transaction to TransactionDTO
     */
    public TransactionDTO toTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    /** Mapping a transaction list to a transaction dto list
     */
    public List<TransactionDTO> toTransactionDTOList(List<Transaction> transactionList) {
        Type listType =  new TypeToken<List<TransactionDTO>>() {}.getType();
        List<TransactionDTO> transactionDTOs = modelMapper.map(transactionList, listType);
        return transactionDTOs;
    }

    static class ToTransactionMapping extends PropertyMap<TransactionDTO, Transaction> {
        /** Define mapping configuration PropertyMap<Source, Destination>**/
        @Override
        protected void configure() {
            map().setTransactionCode(source.getTransactionCode());
            map().setCompanySymbol(source.getCompanySymbol());
            map().setMarketCode(source.getMarketCode());
            skip().setCreatedOn(null);
        }
    }
    static class ToTransactionDTOMapping extends PropertyMap<Transaction, TransactionDTO> {
        @Override
        protected void configure() {
        }
    }

}
