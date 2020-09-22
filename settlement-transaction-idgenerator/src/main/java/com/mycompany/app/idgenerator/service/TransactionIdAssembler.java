package com.mycompany.app.idgenerator.service;

import com.google.common.base.Joiner;
import com.mycompany.app.idgenerator.api.dto.TransactionIdDTO;
import com.mycompany.app.idgenerator.domain.TransactionIdData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionIdAssembler {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionIdAssembler.class);

    public TransactionIdDTO createIdCodeDTO(TransactionIdData idCodeData) {
        LOG.debug("Create id code for {}", idCodeData);
        return new TransactionIdDTO(Joiner.on("").join(idCodeData.getStockExchangePrefix(),idCodeData.getUniqueId()));
    }
}
