package com.person.design.repository;

import com.person.design.vo.Account;
import com.person.design.vo.AccountTransactionDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangchangling on 2018/11/13.
 */
@Component
public class AccountRepository {

    private static Map<Long,Account> chm = new ConcurrentHashMap<>();

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
    public void save(AccountTransactionDetails details){

    }
    public void createAccount2Repository(Account account){
        chm.put(account.getAccountId(),account);
    }
}
