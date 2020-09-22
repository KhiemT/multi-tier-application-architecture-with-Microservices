package com.mycompany.app.transaction.domain;

import com.mycompany.app.transaction.utils.DomainConstants;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;

@Entity
@Table(name="transaction")
@IdClass(TransactionComposeKey.class)
public class Transaction implements DomainConstants {
    @Id
    private String transactionCode;

    @Id
    private String companySymbol;

    @Id
    private String marketCode;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "stock_value")
    private String stockValue;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "client")
    private String client;

    @Column(name = "created_on", updatable = false)
    @Type(type = COMMON_DATE_TYPE)
    private DateTime createdOn = new DateTime();

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionCode='" + transactionCode + '\'' +
                ", companySymbol='" + companySymbol + '\'' +
                ", marketCode=" + marketCode +
                ", transactionType='" + transactionType + '\'' +
                ", stockValue='" + stockValue + '\'' +
                ", commission='" + commission + '\'' +
                ", tax=" + tax +
                ", client=" + client +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (transactionCode != null ? !transactionCode.equals(that.transactionCode) : that.transactionCode != null)
            return false;
        if (companySymbol != null ? !companySymbol.equals(that.companySymbol) : that.companySymbol != null)
            return false;
        return marketCode != null ? marketCode.equals(that.marketCode) : that.marketCode == null;

    }

    @Override
    public int hashCode() {
        int result = transactionCode != null ? transactionCode.hashCode() : 0;
        result = 31 * result + (companySymbol != null ? companySymbol.hashCode() : 0);
        result = 31 * result + (marketCode != null ? marketCode.hashCode() : 0);
        return result;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public String getCompanySymbol() {
        return companySymbol;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getStockValue() {
        return stockValue;
    }

    public Double getCommission() {
        return commission;
    }

    public Double getTax() {
        return tax;
    }

    public String getClient() {
        return client;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }
}
