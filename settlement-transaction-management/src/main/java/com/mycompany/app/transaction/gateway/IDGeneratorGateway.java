package com.mycompany.app.transaction.gateway;

import com.mycompany.app.transaction.api.dto.DatasetSvcDTO;
import com.mycompany.app.transaction.gateway.dto.IdCodeDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IDGeneratorGateway {
    IdCodeDTO getIdCode(String symbol);
    DatasetSvcDTO getDataset() throws IOException, URISyntaxException;
}
