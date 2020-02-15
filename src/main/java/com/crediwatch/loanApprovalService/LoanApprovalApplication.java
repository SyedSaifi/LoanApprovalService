package com.crediwatch.loanApprovalService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanApprovalApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApprovalApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoanApprovalApplication.class, args);
    }
}
