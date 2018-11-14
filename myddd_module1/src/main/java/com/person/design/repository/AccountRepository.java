package com.person.design.repository;

import com.person.design.vo.Account;
import com.person.design.vo.AccountTransactionDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangchangling on 2018/11/13.
 */
@Component
public class AccountRepository {

    private static Map<Long,Account> chm = new ConcurrentHashMap<>();
    private static Map<Long,List<AccountTransactionDetails>> transactionDetails = new ConcurrentHashMap<>();
    public Account getAccount(Long srcAccountId) {
        preHandle(srcAccountId);
        return chm.get(srcAccountId);
    }

    /**
     * 随机金额冻结处理
     * @param srcAccountId
     */
    private void preHandle(Long srcAccountId) {
        Account account = chm.get(srcAccountId);
        account.setFrozenAmount(new BigDecimal(new Random().nextInt(10)));
    }

    /**
     * 交易明细保存
     * @param details
     */
    public void saveTransactionDetails(AccountTransactionDetails details){

        List<AccountTransactionDetails> accountDetails = transactionDetails.get(details.getAccountId());
        if(accountDetails == null) {
            accountDetails = new ArrayList<>();
            transactionDetails.put(details.getAccountId(),accountDetails);
        }
        accountDetails.add(details);
    }

    /**
     * 创建账户
     * @param account
     */
    public void createAccount2Repository(Account account){
        chm.put(account.getAccountId(),account);
    }

    public List<AccountTransactionDetails> queryAccountTransactionDetails(Long accountId){
        return transactionDetails.get(accountId);
    }
}
