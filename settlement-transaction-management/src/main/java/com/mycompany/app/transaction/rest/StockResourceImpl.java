package com.mycompany.app.transaction.rest;

import com.google.common.collect.ImmutableMap;
import com.mycompany.app.transaction.api.StockResource;
import com.mycompany.app.transaction.api.dto.ClosePrice;
import com.mycompany.app.transaction.api.dto.TwoHundredDmaDTO;
import com.mycompany.app.transaction.exception.CommonRestErrors;
import com.mycompany.app.transaction.exception.RestException;
import com.mycompany.app.transaction.exception.RestExceptionHandler;
import com.mycompany.app.transaction.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mycompany.app.transaction.utils.Constraint.APPLICATION_JSON_DEFAULT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Khiem on 12/28/2016.
 */
@RestController
@RequestMapping("/api")
public class StockResourceImpl implements StockResource{

    @Autowired
    private StockService stockService;

    @Override
    @RequestMapping(value = "/v2/{tickerSymbol}/closePrice",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_DEFAULT)
    public ClosePrice getClosePrice(@PathVariable String tickerSymbol,
                                      @RequestParam(value = "startDate") String startDate,
                                      @RequestParam(value = "endDate") String endDate) {

       // try {
            return stockService.getClosePrice(tickerSymbol, startDate, endDate);
       /* } catch (ResourceException e) {
            throw new RestException(e.getError(), e.getMapParams());
        }*/
    }

    @Override
    @RequestMapping(value = "/v2/{tickerSymbol}/200dma",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TwoHundredDmaDTO cal200DaysAvg(@PathVariable String tickerSymbol,
                                                          @RequestParam(value = "startDate") String startDate) {
        TwoHundredDmaDTO twoHundredDmaDTO =  stockService.calculate200DayMovingAvg(tickerSymbol, startDate);

        return twoHundredDmaDTO;
    }
}
