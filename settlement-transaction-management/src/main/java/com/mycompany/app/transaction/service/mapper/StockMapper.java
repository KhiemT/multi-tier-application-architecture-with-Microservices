package com.mycompany.app.transaction.service.mapper;

import com.mycompany.app.transaction.api.dto.*;
import com.mycompany.app.transaction.utils.Constraint;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/28/2016.
 */
@Component
public class StockMapper {

    public ModelMapper modelMapper = new ModelMapper();

    public StockMapper() {
        modelMapper.addMappings(new ToDateCloseMapping());
    }

   /* public List<DateClose> toDateCloseList(List<DailyStockHistory> dailyStockHistories) {
        Type listType =  new TypeToken<List<DateClose>>() {}.getType();
        List<DateClose> DateCloses = modelMapper.map(dailyStockHistories, listType);
        return DateCloses;
    }*/
    public List<String[]> toDateCloseList(List<DailyStockHistory> dailyStockHistories) {
        List<String[]> result = new ArrayList<>();
        dailyStockHistories.forEach(d -> {
            String[] dateClose = {d.getDate().toString(Constraint.DEFAULT_DATE_FORMAT), d.getClose().toString()};
            result.add(dateClose);
        });
        return result;
    }
    static class ToDateCloseMapping extends PropertyMap<DailyStockHistory, DateClose> {
        @Override
        protected void configure() {
            map().setPrice(source.getClose().toString());
        }
    }
    /**
     * Explicit mapping StockSvcDTO to StockDTO
     * @param stockSvc is source mapper.
     * @return StockDTO
     */
    public StockDTO toStockDTO(StockSvcDTO stockSvc) {
        StockDTO stockDTO = new StockDTO();
        DatasetSvcDTO datasetSvc = stockSvc.getDataset();

        DatasetDTO datasetDTO = new DatasetDTO();
        datasetDTO.setId(datasetSvc.getId());
        datasetDTO.setDatasetCode(datasetSvc.getDataset_code());
        datasetDTO.setDatabaseCode(datasetSvc.getDatabase_code());
        datasetDTO.setName(datasetSvc.getName());
        datasetDTO.setRefreshedAt(datasetSvc.getRefreshed_at());
        datasetDTO.setNewestAvailableDate(datasetSvc.getNewest_available_date());
        datasetDTO.setOldestAvailableDate(datasetSvc.getOldest_available_date());
        datasetDTO.setColumnNames(datasetSvc.getColumn_names());
        datasetDTO.setFrequency(datasetSvc.getFrequency());
        datasetDTO.setType(datasetSvc.getType());
        datasetDTO.setPremium(datasetSvc.getPremium());
        datasetDTO.setLimit(datasetSvc.getLimit());
        datasetDTO.setTransform(datasetSvc.getTransform());
        datasetDTO.setColumnIndex(datasetSvc.getColumn_index());
        datasetDTO.setStartDate(datasetSvc.getStart_date());
        datasetDTO.setEndDate(datasetSvc.getEnd_date());
        datasetDTO.setCollapse(datasetSvc.getCollapse());
        datasetDTO.setOrder(datasetSvc.getOrder());
        datasetDTO.setDatabaseId(datasetSvc.getDatabase_id());
        datasetDTO.setDailyStockHistories(toDailyStockHistories(datasetSvc.getData()));

        stockDTO.setDataset(datasetDTO);
        return stockDTO;
    }
    /** Mapping daily stock data history **/
    private List<DailyStockHistory> toDailyStockHistories(List<List<Object>> data) {
        List<DailyStockHistory> dailyStockHistoryList = new ArrayList<>();
        data.forEach(d -> {
            DailyStockHistory dailyStockHistory = new DailyStockHistory();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            dailyStockHistory.setDate(DateTime.parse(d.get(0).toString(),fmt));
            dailyStockHistory.setOpen(Double.valueOf(d.get(1).toString()));
            dailyStockHistory.setHigh(Double.valueOf(d.get(2).toString()));
            dailyStockHistory.setLow(Double.valueOf(d.get(3).toString()));
            dailyStockHistory.setClose(Double.valueOf(d.get(4).toString()));
            dailyStockHistory.setVolume(d.get(5).toString());
            dailyStockHistory.setExDividend(Double.valueOf(d.get(6).toString()));
            dailyStockHistory.setSplitRatio(Double.valueOf(d.get(7).toString()));
            dailyStockHistory.setAdjOpen(Double.valueOf(d.get(8).toString()));
            dailyStockHistory.setAdjHigh(Double.valueOf(d.get(9).toString()));
            dailyStockHistory.setAdjLow(Double.valueOf(d.get(10).toString()));
            dailyStockHistory.setAdjClose(Double.valueOf(d.get(11).toString()));
            dailyStockHistory.setAdjVolume(d.get(12).toString());

            dailyStockHistoryList.add(dailyStockHistory);
        });
        return dailyStockHistoryList;
    }
}
