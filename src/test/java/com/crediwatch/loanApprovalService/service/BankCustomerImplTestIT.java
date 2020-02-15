package com.crediwatch.loanApprovalService.service;

import com.crediwatch.loanApprovalService.model.ApprovalRequest;
import com.crediwatch.loanApprovalService.model.BankCustomer;
import com.crediwatch.loanApprovalService.model.CustomerForm;
import com.crediwatch.loanApprovalService.repository.BankCustomerDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class BankCustomerImplTestIT {
    @Autowired
    BankCustomerImpl iBankCustomer;

    @Autowired
    BankCustomerDao bankCustomerDao;

    private ApprovalRequest approvalRequest;
    private CustomerForm customerForm;
    private BankCustomer bankCustomer;

    @Before
    public void setUp() throws Exception {
        customerForm = new CustomerForm();
        customerForm.setCin("TEST_CIN");
        customerForm.setCompanyClass("TEST_CLASS");
        customerForm.setCompanyType("TEST_TYPE");
        customerForm.setCompanyName("TEST_COMPANY");
        customerForm.setEmail("test@TEST.COM");

        approvalRequest = new ApprovalRequest();
        approvalRequest.setCin("TEST_CIN");
        approvalRequest.setDuration("5");
        approvalRequest.setLoanAmount("5000");

        bankCustomer = new BankCustomer();
        bankCustomer.setCin("TEST_CIN");
        bankCustomer.setCompanyClass("TEST_CLASS");
        bankCustomer.setCompanyType("TEST_TYPE");
        bankCustomer.setCompanyName("TEST_COMPANY");
        bankCustomer.setEmail("test@TEST.COM");
        bankCustomerDao.save(bankCustomer);
    }

    @Test
    public void testVerify() {
        assertTrue(iBankCustomer.verify(customerForm));
    }

    @Test
    public void testVerifyWithNullCustomerForm() {
        assertFalse(iBankCustomer.verify(null));
    }

    @Test
    public void testVerifyForInvalidCustomer() {
        customerForm.setCin("INVALID_CIN");
        assertFalse(iBankCustomer.verify(customerForm));
    }

    @Test
    public void testVerifyForInvalidEmail() {
        customerForm.setEmail("INVALID_EMAIL");
        assertFalse(iBankCustomer.verify(customerForm));
    }

    @Test
    public void testVerifyForInvalidCompanyType() {
        customerForm.setEmail("INVALID_COMPANY_TYPE");
        assertFalse(iBankCustomer.verify(customerForm));
    }

    @Test
    public void testVerifyForInvalidCompanyName() {
        customerForm.setEmail("INVALID_COMPANY_NAME");
        assertFalse(iBankCustomer.verify(customerForm));
    }

    @Test
    public void testVerifyForInvalidCompanyClass() {
        customerForm.setEmail("INVALID_COMPANY_CLASS");
        assertFalse(iBankCustomer.verify(customerForm));
    }

    @Test
    public void testApprove() {
        bankCustomer.setCompanyStatus("active");
        bankCustomer.setX1("0.9206685102973374");
        bankCustomer.setX2("0.7832036502397518");
        bankCustomer.setX3("0.9991168368669596");
        bankCustomer.setX4("0.026549592473913197");
        bankCustomer.setX5("0.92066851");
        bankCustomer.setAuthorizedCapital("50000");
        bankCustomer.setPaidUpCapital("5000");
        bankCustomerDao.save(bankCustomer);
        assertTrue(iBankCustomer.approveLoan(approvalRequest));
    }

    @Test
    public void testApproveWithNullApprovalRequest() {
        assertFalse(iBankCustomer.approveLoan(null));
    }

    @Test
    public void testApproveForInvalidCustomer() {
        approvalRequest.setCin("INVALID_CIN");
        assertFalse(iBankCustomer.approveLoan(approvalRequest));
    }

    @Test
    public void testApproveForInactiveCustomer() {
        bankCustomer.setCompanyStatus("Not available for efiling");
        bankCustomerDao.save(bankCustomer);
        assertFalse(iBankCustomer.approveLoan(approvalRequest));
    }

    @Test(expected = NullPointerException.class)
    public void testApproveForInvalidApprovalRequestFormWithNullDuration() {
        approvalRequest.setDuration(null);
        iBankCustomer.approveLoan(approvalRequest);
    }

    @Test(expected = NullPointerException.class)
    public void testApproveForInvalidApprovalRequestFormWithNullLoanAmount() {
        approvalRequest.setLoanAmount(null);
        iBankCustomer.approveLoan(approvalRequest);
    }

    @Test
    public void testApproveForInvalidApprovalRequestFormWithEmptyDuration() {
        approvalRequest.setDuration("");
        assertFalse(iBankCustomer.approveLoan(approvalRequest));
    }

    @Test
    public void testApproveForInvalidApprovalRequestFormWithEmptyLoanAmount() {
        approvalRequest.setLoanAmount("");
        assertFalse(iBankCustomer.approveLoan(approvalRequest));
    }

    @Test
    public void testApproveForBigLoanAmountPerYear() {
        bankCustomer.setCompanyStatus("active");
        bankCustomer.setAuthorizedCapital("50000");
        bankCustomer.setPaidUpCapital("5000");
        bankCustomerDao.save(bankCustomer);
        approvalRequest.setLoanAmount("500000");
        assertFalse(iBankCustomer.approveLoan(approvalRequest));
    }

    @Test
    public void testApproveWhenScoreIsLesserThanThreshold() {
        bankCustomer.setCompanyStatus("active");
        bankCustomer.setX1("0.9206685102973374");
        bankCustomer.setX2("0.7832036502397518");
        bankCustomer.setX3("0");
        bankCustomer.setX4("0.026549592473913197");
        bankCustomer.setX5("0");
        bankCustomer.setAuthorizedCapital("50000");
        bankCustomer.setPaidUpCapital("5000");
        bankCustomerDao.save(bankCustomer);
        assertFalse(iBankCustomer.approveLoan(approvalRequest));
    }

    @After
    public void tearDown() throws Exception {
        bankCustomerDao.deleteById("TEST_CIN");
    }

}
