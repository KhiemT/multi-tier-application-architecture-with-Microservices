package com.mycompany.app.idgenerator.domain;

import javax.persistence.*;

import com.mycompany.app.idgenerator.utils.AbstractEntity;
import com.mycompany.app.idgenerator.utils.Identifiable;

@Entity
@Table(name="id_code_data")
public class TransactionIdData extends AbstractEntity implements Identifiable{

	@Id
    @Column(name="symbol")
    private String symbol;

    @Column(name="company_name")
    private String companyName;

    @Column(name="stock_exchange_prefix")
    private String stockExchangePrefix;

	@Column(name="unique_id")
    private Long uniqueId;
    
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStockExchangePrefix() {
		return stockExchangePrefix;
	}

	public void setStockExchangePrefix(String stockExchangePrefix) {
		this.stockExchangePrefix = stockExchangePrefix;
	}

	public Long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}
    
}
