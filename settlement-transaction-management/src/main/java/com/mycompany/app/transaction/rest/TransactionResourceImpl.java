package com.mycompany.app.transaction.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.mycompany.app.transaction.api.dto.TransactionDTO;
import com.mycompany.app.transaction.service.TransactionService;
import com.mycompany.app.transaction.utils.Pagination;
import com.mycompany.app.transaction.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.app.transaction.api.TransactionResource;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class TransactionResourceImpl implements TransactionResource {
    private static final String APPLICATION_JSON_DEFAULT_CHARSET =
            APPLICATION_JSON_VALUE + ";charset=UTF-8";
    @Autowired
    private TransactionService transactionService;



    @Override
    @RequestMapping(value = "/transaction/add", method = RequestMethod.POST,
            produces = APPLICATION_JSON_DEFAULT_CHARSET)
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
    	/*try{
            transactionService.saveTransaction(transactionDTO);
    		 return new ResponseEntity<>(HttpStatus.OK);
    	} catch (RestException e) {
            throw new RestException(e.getRestError(), e.getErrorParams());
        }*/
        return null;
    }

    @Override
    @RequestMapping(value = "transaction/{companySymbol}/{transactionCode}",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_DEFAULT_CHARSET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO findTransaction(@PathVariable String companySymbol, @PathVariable String transactionCode) {
        return transactionService.findByTransactionCodeAndCompanySymbol(companySymbol, transactionCode);
    }

    @Override
    @RequestMapping(value = "/transaction/list", method = RequestMethod.GET,
            produces = APPLICATION_JSON_DEFAULT_CHARSET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Pagination<TransactionDTO> getTransactionList(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        return transactionService.getAllTransactionsByPaging(page, pageSize);
    }

    @Override
    @RequestMapping(value = "/dataset", method = RequestMethod.GET,
            produces = APPLICATION_JSON_DEFAULT_CHARSET)
    @ResponseStatus(HttpStatus.OK)
    public void getDataSet() throws IOException, URISyntaxException {
        transactionService.getDataSet();
    }
}
