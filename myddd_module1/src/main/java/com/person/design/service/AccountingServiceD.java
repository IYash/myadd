package com.person.design.service;

import com.person.design.exception.AccountingServiceException;
import com.person.design.repository.AccountRepository;
import com.person.design.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Predicate;

/**
 * Created by huangchangling on 2018/11/14.
 * 通过DDD方式设计后的账户服务
 */
@Service("accountServiceD")
public class AccountingServiceD implements AccountingService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void transfer(Long srcAccountId, Long destAccountId, BigDecimal amount) throws AccountingServiceException {
        Account srcAccount = accountRepository.getAccount(srcAccountId);
        Account destAccount = accountRepository.getAccount(destAccountId);
        Predicate<Account> predicate = account -> account.getBalance().subtract(srcAccount.getFrozenAmount()).compareTo(amount) > 0;
        srcAccount.checkBalance(predicate,"余额不足");
        srcAccount.transferTo(amount,destAccount);
        accountRepository.saveTransactionDetails(srcAccount.getTransactionDetailses().get(0));
        accountRepository.saveTransactionDetails(destAccount.getTransactionDetailses().get(0));
    }
}
