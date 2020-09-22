package com.mycompany.app.transaction.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Defines the compound key used by the Transaction entity for persistence
 */
@Embeddable
public class TransactionComposeKey implements Serializable {
    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "company_symbol")
    private String companySymbol;

    @Column(name = "market_code")
    private String marketCode;
    // For JPA only
    protected TransactionComposeKey() {}

    public TransactionComposeKey(String transactionCode, String companySymbol, String marketCode) {
        this.transactionCode = transactionCode;
        this.companySymbol = companySymbol;
        this.marketCode = marketCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionComposeKey that = (TransactionComposeKey) o;

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
}
