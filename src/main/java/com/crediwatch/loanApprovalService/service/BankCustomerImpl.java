package com.crediwatch.loanApprovalService.service;

import com.crediwatch.loanApprovalService.model.ApprovalRequest;
import com.crediwatch.loanApprovalService.model.BankCustomer;
import com.crediwatch.loanApprovalService.model.CustomerForm;
import com.crediwatch.loanApprovalService.repository.BankCustomerDao;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankCustomerImpl implements IBankCustomer{

    private static final Logger LOGGER = LoggerFactory.getLogger(BankCustomerImpl.class);

    private static final String NOT_AVAILABLE_FOR_EFILING = "Not available for efiling";
    public static final int MAX_ASSET_CAPITAL = 990000000;
    public static final int MIN_ASSET_CAPITAL = 10;
    public static final double SCORE_THRESHOLD = 2.464;

    @Autowired
    BankCustomerDao bankCustomerDao;

    @Override
    public String saveBankCustomers() throws IOException {
        List<String[]> allData = null;
        BankCustomer bankCustomer;
        List<BankCustomer> bankCustomers = new ArrayList<>();
        FileReader filereader = new FileReader("src/main/resources/data_set.csv");
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .build();
        allData = csvReader.readAll();
        for(String[] customer: allData){
            bankCustomer = new BankCustomer();
            bankCustomer.setCin(customer[0]);
            bankCustomer.setCompanyName(customer[1]);
            bankCustomer.setDateOfRegistration(customer[2]);
            bankCustomer.setMonthName(customer[3]);
            bankCustomer.setState(customer[4]);
            bankCustomer.setRoc(customer[5]);
            bankCustomer.setCompanyStatus(customer[6]);
            bankCustomer.setCategory(customer[7]);
            bankCustomer.setCompanyClass(customer[8]);
            bankCustomer.setCompanyType(customer[9]);
            bankCustomer.setAuthorizedCapital(customer[10]);
            bankCustomer.setPaidUpCapital(customer[11]);
            bankCustomer.setActivityCode(customer[12]);
            bankCustomer.setActivityDescription(customer[13]);
            bankCustomer.setRegisteredOfficeAddress(customer[14]);
            bankCustomer.setEmail(customer[15]);
            bankCustomer.setX1(customer[16]);
            bankCustomer.setX2(customer[17]);
            bankCustomer.setX3(customer[18]);
            bankCustomer.setX4(customer[19]);
            bankCustomer.setX5(customer[20]);
            bankCustomers.add(bankCustomer);
        }
        bankCustomerDao.saveAll(bankCustomers);
        LOGGER.info("Succussfully uploaded all the records");
        return "SUCCESS";
    }

    @Override
    public Boolean verify(CustomerForm customerForm) {
        if(customerForm == null)
            return false;

        Optional<BankCustomer> byCin = bankCustomerDao.findById(customerForm.getCin());
        if(!byCin.isPresent()){
            return false;
        }

        BankCustomer bankCustomer = byCin.get();

        if(customerForm.getCompanyClass().equalsIgnoreCase(bankCustomer.getCompanyClass()) &&
                customerForm.getCompanyName().equalsIgnoreCase(bankCustomer.getCompanyName()) &&
                customerForm.getCompanyType().equalsIgnoreCase(bankCustomer.getCompanyType())&&
                customerForm.getEmail().equalsIgnoreCase(bankCustomer.getEmail())){
            LOGGER.info("The customer is valid");
            return true;
        }
        return false;
    }

    @Override
    public Boolean approveLoan(ApprovalRequest approvalRequest) {
        if(approvalRequest == null || approvalRequest.getDuration().isEmpty() || approvalRequest.getLoanAmount().isEmpty())
            return false;

        Optional<BankCustomer> byCin = bankCustomerDao.findById(approvalRequest.getCin());
        if(!byCin.isPresent()){
            return false;
        }
        BankCustomer bankCustomer = byCin.get();
        if(bankCustomer.getCompanyStatus().equalsIgnoreCase(NOT_AVAILABLE_FOR_EFILING)){
            LOGGER.info("The company {} is not available for efiling, hence the loan is rejected.",
                    approvalRequest.getCin());
            return false;
        }

        Integer assetCapital = Integer.parseInt(bankCustomer.getAuthorizedCapital()) -
                Integer.parseInt(bankCustomer.getPaidUpCapital());

        int loanAmtPerYear = Integer.parseInt(approvalRequest.getLoanAmount())/
                Integer.parseInt(approvalRequest.getDuration());

        if(assetCapital < loanAmtPerYear){
            return false;
        }

        Integer normalizedAssetCapital = (assetCapital - MIN_ASSET_CAPITAL)/(MAX_ASSET_CAPITAL - MIN_ASSET_CAPITAL);

        double score = 0.717*Double.parseDouble(bankCustomer.getX1()) +
                0.847*Double.parseDouble((bankCustomer.getX2())) +
                3.107*Double.parseDouble((bankCustomer.getX3())) +
                0.420*Double.parseDouble((bankCustomer.getX4())) +
                0.998*Double.parseDouble((bankCustomer.getX5())) +
                normalizedAssetCapital;

        if(score >= SCORE_THRESHOLD){
            LOGGER.info("Loan is approved as it meets the threshold score");
            return true;
        }
        return false;
    }

    @Override
    public List<BankCustomer> fetchAllData() {
        return bankCustomerDao.findAll();
    }
}
