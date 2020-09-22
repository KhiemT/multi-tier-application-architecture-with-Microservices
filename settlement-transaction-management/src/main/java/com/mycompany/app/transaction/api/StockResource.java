package com.mycompany.app.transaction.api;

import com.mycompany.app.transaction.api.dto.ClosePrice;
import com.mycompany.app.transaction.api.dto.TwoHundredDmaDTO;

/**
 * Created by Admin on 12/28/2016.
 */
public interface StockResource {
    /**
     * @api GET api/v2/{tickerSymbol}/closePrice?startDate={startDate}&endDate={endDate}
     * @apiName get Close Price.
     * @apiParam tickerSymbol company symbol in market place.
     * @apiParam startDate a day to end.
     * @apiParam endDate a day to from.
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *           "price": {
     *               "ticker": "GE",
     *               "dateClose": [
     *                       ["2016-12-22", "31.82" ],
     *                       ["2016-12-21", "32.13" ]
     *                ]
     *            }
     *      }
     * @apiError (404) STOCK_NOT_FOUND something unexpected happens namely invalid ticker symbol.
     * @apiErrorExample Error-Response:
     *      {
     *          "errorCode": "STOCK_NOT_FOUND",
     *          "errorMessage": "Stock is not found",
     *          "params": {
     *              "Company stock symbol": "{XXX} was not found."
     *      }
    }
     */
    ClosePrice getClosePrice(String tickerSymbol,String startDate, String endDate);

    /**
     * @api GET api/v2/{tickerSymbol}/200dma?startDate={startDate}
     * @apiDescription calculate 200 days avg.
     * @apiParam tickerSymbol company symbol in market place.
     * @apiParam startDate a day to end.
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 200 OK
     *  {
     *       "200dma": {
     *          "ticker": "GE",
     *          "avg": 30.613913043478263
     *       }
     *  }
     *  @apiError (404) STOCK_NOT_FOUND something unexpected happens namely invalid ticker symbol.
     *  @apiErrorExample Error-Response:
     *  {
            "errorCode": "START_DATE_DATA_NOT_FOUND",
            "errorMessage": "Start date data is not found",
            "params": {
            "Newest Available Date": "the first possible startdate is suggested:{2016-12-28}"
        }
    }
     */
    TwoHundredDmaDTO cal200DaysAvg(String tickerSymbol, String startDate);
}
