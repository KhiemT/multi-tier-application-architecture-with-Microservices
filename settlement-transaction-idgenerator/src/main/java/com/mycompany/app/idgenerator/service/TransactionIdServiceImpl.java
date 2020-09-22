package com.mycompany.app.idgenerator.service;


import com.mycompany.app.idgenerator.utils.SymbolCodeDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.app.idgenerator.api.dto.TransactionIdDTO;
import com.mycompany.app.idgenerator.domain.TransactionIdData;
import com.mycompany.app.idgenerator.repository.TransactionIdDataRepository;

@Service
@Transactional
public class TransactionIdServiceImpl implements TransactionIdService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionIdServiceImpl.class);

    @Autowired
    TransactionIdDataRepository idCodeDataRepository;

    @Autowired
    TransactionIdAssembler idCodeAssembler;

    public TransactionIdDTO generateIdentificationCode(String symbol) throws SymbolCodeDoesNotExistException {
        LOG.debug("Service is called with symbol {}", symbol);
        
        TransactionIdData idCodeData;
        try {
            idCodeData = idCodeDataRepository.getIdCodeDataWithNextUniqueId(symbol);
        } catch (EmptyResultDataAccessException ex){
            LOG.error("Id generation failed for symbol {}", symbol,ex);
            throw new SymbolCodeDoesNotExistException(symbol);
        }
        TransactionIdDTO idCodeDTO = idCodeAssembler.createIdCodeDTO(idCodeData);
        LOG.debug("Generated idCodeDTO: {}", idCodeDTO);
        return idCodeDTO;
    }
}
