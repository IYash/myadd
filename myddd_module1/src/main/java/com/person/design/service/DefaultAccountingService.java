package com.person.design.service;

import com.person.design.exception.AccountingServiceException;
import com.person.design.repository.AccountRepository;
import com.person.design.vo.Account;
import com.person.design.vo.AccountTransactionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Predicate;

/**
 * Created by huangchangling on 2018/11/13.
 */
@Service
public class DefaultAccountingService implements AccountingService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void transfer(Long srcAccountId, Long destAccountId, BigDecimal amount) throws AccountingServiceException {
        Account srcAccount = accountRepository.getAccount(srcAccountId);
        Account destAccount = accountRepository.getAccount(destAccountId);
        //需求1.0.1版本
        if(!srcAccount.isActive() || !destAccount.isActive()) throw new AccountingServiceException("账号未激活");
        BigDecimal availableAmount = srcAccount.getBalance().subtract(srcAccount.getFrozenAmount());
        //需求1.0版本
        //Predicate<Account> predicate = account -> account.getBalance().compareTo(amount) > 0;
        //boolean enough = predicate.test(srcAccount);

        //需求1.0.1对enough逻辑的修正
        Predicate<BigDecimal> predicate = available -> available.compareTo(amount) > 0;
        boolean enough = predicate.test(availableAmount);

        if(!enough) throw new AccountingServiceException("余额不足");
        srcAccount.setBalance(srcAccount.getBalance().subtract(amount));
        destAccount.setBalance(destAccount.getBalance().add(amount));
        //需求1.0.2
        AccountTransactionDetails details = new AccountTransactionDetails();
        accountRepository.saveTransactionDetails(details);
    }
}
