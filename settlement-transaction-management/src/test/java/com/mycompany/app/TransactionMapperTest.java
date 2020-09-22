package com.mycompany.app;

import com.mycompany.app.transaction.service.mapper.TransactionMapper;
import org.junit.Test;
/** Test validation mapping for transaction **/
public class TransactionMapperTest {
    @Test
    public void validateMappingTest(){
        TransactionMapper transactionMapper = new TransactionMapper();
        transactionMapper.modelMapper.validate();
    }
}
