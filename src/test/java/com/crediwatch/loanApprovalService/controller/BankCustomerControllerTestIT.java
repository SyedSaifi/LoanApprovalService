package com.crediwatch.loanApprovalService.controller;

import com.crediwatch.loanApprovalService.model.ApprovalRequest;
import com.crediwatch.loanApprovalService.model.BankCustomer;
import com.crediwatch.loanApprovalService.model.CustomerForm;
import com.crediwatch.loanApprovalService.repository.BankCustomerDao;
import com.crediwatch.loanApprovalService.service.BankCustomerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class BankCustomerControllerTestIT {

    @Autowired
    BankCustomerDao bankCustomerDao;

    @Autowired
    TestRestTemplate testRestTemplate;

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
    public void testVerify() throws Exception {
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/verify", customerForm, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testVerifyWithNullCustomerForm() throws Exception {
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/verify", null, String.class);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
    }

    @Test
    public void testVerifyForInvalidCustomer() throws Exception {
        customerForm.setCin("INVALID_CIN");
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/verify", customerForm, String.class);
        assertEquals("false", responseEntity.getBody());
    }

    @Test
    public void testVerifyForInvalidCustomerForm() throws Exception {
        customerForm.setCin(null);
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/verify", customerForm, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testApprove() throws Exception {
        bankCustomer.setCompanyStatus("active");
        bankCustomer.setX1("0.9206685102973374");
        bankCustomer.setX2("0.7832036502397518");
        bankCustomer.setX3("0.9991168368669596");
        bankCustomer.setX4("0.026549592473913197");
        bankCustomer.setX5("0.92066851");
        bankCustomer.setAuthorizedCapital("50000");
        bankCustomer.setPaidUpCapital("5000");
        bankCustomerDao.save(bankCustomer);
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/approve", approvalRequest, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testApproveWithNullApprovalRequest() throws Exception {
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/approve", null, String.class);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
    }

    @Test
    public void testApproveForRejection() throws Exception {
        bankCustomer.setCompanyStatus("Not available for efiling");
        bankCustomerDao.save(bankCustomer);
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/approve", approvalRequest, String.class);
        assertEquals("false", responseEntity.getBody());
    }

    @Test
    public void testApproveForInvalidApprovalForm() throws Exception {
        approvalRequest.setCin(null);
        ResponseEntity responseEntity = testRestTemplate.postForEntity("/approve", approvalRequest, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testFetchAllData() throws Exception {
        UriComponentsBuilder builder =fromUriString("/fetchAllData");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<List> responseEntity1 = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        assertTrue(responseEntity1.getStatusCode().equals(HttpStatus.OK));
        assertTrue(responseEntity1.getBody().size() == 1);
    }

    private <T> byte[] convertObjectToJsonBytes(T model) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonText = null;
        try {
            jsonText = ow.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonText.getBytes();
    }

    @After
    public void tearDown() throws Exception {
        bankCustomerDao.deleteById("TEST_CIN");
    }
}
