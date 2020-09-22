package com.mycompany.app.transaction.api.dto;

import java.util.List;

/**
 * Created by Admin on 12/28/2016.
 */
public class DatasetDTO {
    private String id;
    private String datasetCode;
    private String databaseCode;
    private String name;
    private String description;
    private String refreshedAt;
    private String newestAvailableDate;
    private String oldestAvailableDate;
    private String frequency;
    private String type;
    private String premium;
    private String limit;
    private String transform;
    private String columnIndex;
    private String startDate;
    private String endDate;
    private String collapse;
    private String order;
    private String databaseId;
    private List<String> columnNames;
    private List<DailyStockHistory> dailyStockHistories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasetCode() {
        return datasetCode;
    }

    public void setDatasetCode(String datasetCode) {
        this.datasetCode = datasetCode;
    }

    public String getDatabaseCode() {
        return databaseCode;
    }

    public void setDatabaseCode(String databaseCode) {
        this.databaseCode = databaseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefreshedAt() {
        return refreshedAt;
    }

    public void setRefreshedAt(String refreshedAt) {
        this.refreshedAt = refreshedAt;
    }

    public String getNewestAvailableDate() {
        return newestAvailableDate;
    }

    public void setNewestAvailableDate(String newestAvailableDate) {
        this.newestAvailableDate = newestAvailableDate;
    }

    public String getOldestAvailableDate() {
        return oldestAvailableDate;
    }

    public void setOldestAvailableDate(String oldestAvailableDate) {
        this.oldestAvailableDate = oldestAvailableDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getTransform() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }

    public String getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(String columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCollapse() {
        return collapse;
    }

    public void setCollapse(String collapse) {
        this.collapse = collapse;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<DailyStockHistory> getDailyStockHistories() {
        return dailyStockHistories;
    }

    public void setDailyStockHistories(List<DailyStockHistory> dailyStockHistories) {
        this.dailyStockHistories = dailyStockHistories;
    }
}
