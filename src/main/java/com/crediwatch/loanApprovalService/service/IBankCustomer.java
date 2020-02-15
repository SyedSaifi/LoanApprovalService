package com.crediwatch.loanApprovalService.service;

import com.crediwatch.loanApprovalService.model.ApprovalRequest;
import com.crediwatch.loanApprovalService.model.BankCustomer;
import com.crediwatch.loanApprovalService.model.CustomerForm;

import java.io.IOException;
import java.util.List;

public interface IBankCustomer {
    String saveBankCustomers() throws IOException;

    Boolean verify(CustomerForm customerForm);

    Boolean approveLoan(ApprovalRequest approvalRequest);

    List<BankCustomer> fetchAllData();
}
