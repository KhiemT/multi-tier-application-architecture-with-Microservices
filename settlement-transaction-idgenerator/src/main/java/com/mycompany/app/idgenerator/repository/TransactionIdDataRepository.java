package com.mycompany.app.idgenerator.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mycompany.app.idgenerator.domain.TransactionIdData;

@Repository
public class TransactionIdDataRepository {

        @PersistenceContext
        private EntityManager entityManager;

        public TransactionIdData getIdCodeDataWithNextUniqueId(String symbol) {
            Query query = entityManager.createNativeQuery("SELECT nextval('seq_unique_id') as unique_id, symbol,company_name,stock_exchange_prefix FROM id_code_data WHERE symbol=:symbol", TransactionIdData.class);
            query.setParameter("symbol",symbol);
            return (TransactionIdData) query.getSingleResult();
        }
}
