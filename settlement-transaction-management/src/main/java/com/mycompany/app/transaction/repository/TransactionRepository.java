package com.mycompany.app.transaction.repository;

import com.mycompany.app.transaction.domain.Transaction;
import com.mycompany.app.transaction.domain.TransactionComposeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, TransactionComposeKey> {

    @Query(value="from Transaction t where t.transactionCode = :transactionCode " +
            "and t.companySymbol = :companySymbol")
    Optional<Transaction> findByTransactionCodeAndCompanySymbol(
            @Param(value = "companySymbol") String companySymbol,
            @Param(value = "transactionCode") String transactionCode);
}
