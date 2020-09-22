package com.mycompany.app.idgenerator.rest;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.app.idgenerator.api.IdGeneratorResource;
import com.mycompany.app.idgenerator.api.dto.TransactionIdDTO;
import com.mycompany.app.idgenerator.service.TransactionIdService;

@RestController
@RequestMapping("/api")
public class TransactionIdGeneratorResourceImpl implements IdGeneratorResource {

    @Autowired
    private TransactionIdService idCodeService;

    @RequestMapping(value = "tidgenerator",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionIdDTO generateIdentificationCode(@RequestParam(value = "symbol") String symbol) {
        return idCodeService.generateIdentificationCode(symbol);
    }

}
