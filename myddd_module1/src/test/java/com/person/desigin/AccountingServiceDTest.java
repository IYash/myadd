package com.person.desigin;



import com.person.design.repository.AccountRepository;
import com.person.design.service.AccountingService;
import com.person.design.vo.Account;
import com.person.design.vo.AccountTransactionDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by huangchangling on 2018/11/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AccountingServiceDTest {

    @Autowired
    private AccountingService accountServiceD;

    @Autowired
    private AccountRepository accountRepository;

    private Long srcId = 10000L;
    private Long destId = 10086L;
    @Before
    public void initContext(){
        Account srcAccount = new Account(srcId,new BigDecimal(100));
        Account destAccount = new Account(destId,new BigDecimal(10));
        srcAccount.setActive(true);
        destAccount.setActive(true);
        accountRepository.createAccount2Repository(srcAccount);
        accountRepository.createAccount2Repository(destAccount);
    }

    @Test
    public void testTransfer(){
        accountServiceD.transfer(10000L,10086L,new BigDecimal(1000));
        AccountTransactionDetails details = accountRepository.queryAccountTransactionDetails(srcId).get(0);
        System.out.println(details.toJsonString());
    }
}
