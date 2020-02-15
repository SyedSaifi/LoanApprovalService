package com.crediwatch.loanApprovalService.repository;

import com.crediwatch.loanApprovalService.model.BankCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCustomerDao extends MongoRepository<BankCustomer, String> {
}
