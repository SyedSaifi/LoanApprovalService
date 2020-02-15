package com.crediwatch.loanApprovalService.controller;

import com.crediwatch.loanApprovalService.model.ApprovalRequest;
import com.crediwatch.loanApprovalService.model.BankCustomer;
import com.crediwatch.loanApprovalService.model.CustomerForm;
import com.crediwatch.loanApprovalService.service.BankCustomerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @author ML Avengers
 * @version 0.0.1-SNAPSHOT
 */
@Api(tags = {"crediwatch"}, description = "All operation related to crediwatch a loan application.")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BankCustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankCustomerController.class);

    @Autowired
    BankCustomerImpl bankCustomerService;

    @ApiOperation(value = "This api upload the loan dataset csv to mongodb as document.",
            notes = "Requires upload path url", authorizations = {@Authorization(value="basicAuth")})
    @GetMapping(value = "/uploadAllData")
    public String uploadAllData(){
        try {
            bankCustomerService.saveBankCustomers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    @ApiOperation(value = "This api returns all the records from mongodb as document.",
            notes = "Requires upload path url", authorizations = {@Authorization(value="basicAuth")})
    @GetMapping(value = "/fetchAllData")
    public ResponseEntity fetchAllData(){
        LOGGER.info("Fetching all the records from DB.");

        List<BankCustomer> bankCustomers = bankCustomerService.fetchAllData();
        return new ResponseEntity<>(bankCustomers, HttpStatus.OK);
    }

    @ApiOperation(value = "This api verifies if the customer exist is valid",
            notes = "This api verifies if the customer exist is valid",
            authorizations = {@Authorization(value="basicAuth")})
    @PostMapping(value= "/verify")
    public ResponseEntity verify(@NotNull
                                 @RequestBody CustomerForm customerForm) {
        Boolean status = bankCustomerService.verify(customerForm);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @ApiOperation(value = "This api verifies if the customer qualifies to get an approval for the loan",
            notes = "This api verifies if the customer qualifies to get an approval for the loan",
            authorizations = {@Authorization(value="basicAuth")})
    @PostMapping(value= "/approve")
    public ResponseEntity approve(@NotNull
                                  @RequestBody ApprovalRequest approvalRequest) {

        Boolean approve = bankCustomerService.approveLoan(approvalRequest);
        return new ResponseEntity<>(approve, HttpStatus.OK);
    }
}
