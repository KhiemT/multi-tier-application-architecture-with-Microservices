package com.mycompany.app.idgenerator.service;

import com.mycompany.app.idgenerator.api.dto.TransactionIdDTO;
import com.mycompany.app.idgenerator.utils.SymbolCodeDoesNotExistException;

public interface TransactionIdService {
    TransactionIdDTO generateIdentificationCode(String entityTypeCode) throws SymbolCodeDoesNotExistException;
}
