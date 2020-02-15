package com.crediwatch.loanApprovalService.controller;

import com.crediwatch.loanApprovalService.BankCustomerController;
import com.crediwatch.loanApprovalService.model.ApprovalRequest;
import com.crediwatch.loanApprovalService.model.BankCustomer;
import com.crediwatch.loanApprovalService.model.CustomerForm;
import com.crediwatch.loanApprovalService.service.BankCustomerImpl;
import com.crediwatch.loanApprovalService.service.IBankCustomer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = BankCustomerController.class)
public class BankCustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankCustomerImpl iBankCustomer;

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
        approvalRequest.setLoanAmount("500000");

        bankCustomer = new BankCustomer();
        bankCustomer.setCin("TEST_CIN");
        bankCustomer.setCompanyClass("TEST_CLASS");
        bankCustomer.setCompanyType("TEST_TYPE");
        bankCustomer.setCompanyName("TEST_COMPANY");
        bankCustomer.setEmail("test@TEST.COM");
    }

    @Test
    public void testUploadAllData() {

    }

    @Test
    public void testVerify() throws Exception {
        when(iBankCustomer.verify(customerForm)).thenReturn(true);

        mockMvc.perform(post("/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(customerForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void testApprove() throws Exception {
        when(iBankCustomer.approveLoan(approvalRequest)).thenReturn(true);

        mockMvc.perform(post("/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(approvalRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchAllData() throws Exception {
        when(iBankCustomer.fetchAllData()).thenReturn(Arrays.asList(bankCustomer));

        mockMvc.perform(get("/fetchAllData")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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

    }
}
