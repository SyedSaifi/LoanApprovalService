package com.crediwatch.loanApprovalService.service;

import com.crediwatch.loanApprovalService.repository.BankCustomerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BankCustomerImplTest {

    @Mock
    BankCustomerDao bankCustomerDao;

    @InjectMocks
    private BankCustomerImpl iBankCustomer;

    @Test
    public void testSaveBankCustomers() throws IOException {
        when(bankCustomerDao.saveAll(anyList())).thenReturn(new ArrayList<>());
        String response = iBankCustomer.saveBankCustomers();
        assertEquals("SUCCESS", response);
    }
}
