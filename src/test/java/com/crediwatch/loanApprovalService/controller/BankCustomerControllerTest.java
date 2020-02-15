package com.crediwatch.loanApprovalService.controller;

import com.crediwatch.loanApprovalService.service.BankCustomerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = BankCustomerController.class)
public class BankCustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankCustomerImpl iBankCustomer;

    @Test
    public void testUploadAllData() throws Exception {
        when(iBankCustomer.saveBankCustomers()).thenReturn("SUCCESS");
        mockMvc.perform(get("/uploadAllData")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test()
    public void testUploadAllDataWithException() throws Exception {
        when(iBankCustomer.saveBankCustomers()).thenThrow(IOException.class);
        mockMvc.perform(get("/uploadAllData")
                .contentType(MediaType.APPLICATION_JSON));
    }
}
