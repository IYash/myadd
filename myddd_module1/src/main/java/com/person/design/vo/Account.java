package com.person.design.vo;

import com.google.common.base.Preconditions;
import com.person.design.exception.AccountingServiceException;
import lombok.Data;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by huangchangling on 2018/11/13.
 * 账户，是整个系统最核心的业务对象，它包括一下属性：
 * 对象标识，账户号，是否有效标识，余额，冻结金额，账户交易明细集合，账户信用等级
 */
@Data
public class Account implements Serializable{
    private static final long serialVersionUID = -750971609267093823L;

    private Long accountId;
    private String number;
    private BigDecimal balance;
    private BigDecimal frozenAmount;//冻结金额
    private boolean active;
    private List<AccountTransactionDetails> transactionDetailses = new ArrayList<>();
    private AccountCreditDegree creditDegree;
    public Account() {
        this(null);
    }

    public Account(Long accountId) {
        this(accountId,null);
    }

    public Account(Long accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    //根据职责单一的原则，划分需求

    /**
     * 向银行账户中存入金额，贷记
     * @param credit
     */
    private void credit(BigDecimal credit){
        this.getBalance().add(credit);
        createTransactionDetails(new Date(),credit, AccountTransactionDetails.TRANSACTION_TYPE.RECEIPTS.name());
    }

    /**
     * 从银行账户划出金额，借记
     * @param debit
     */
    private void debit(BigDecimal debit){
        this.getBalance().subtract(debit);
        createTransactionDetails(new Date(),debit,AccountTransactionDetails.TRANSACTION_TYPE.EXPENDITURE.name());
    }

    /**
     * 把固定金额转入指定账户
     * @param amount
     * @param destAccount
     */
    public void transferTo(BigDecimal amount,Account destAccount){
        Preconditions.checkArgument(this.isActive(),"支出账户未激活");
        Preconditions.checkArgument(destAccount.isActive(),"目标账户未激活");
        destAccount.credit(amount);
        debit(amount);
    }

    /**
     * 根据需要传入不同的predicate
     * @param predicate
     * @param errorMsg
     */
    public void checkBalance(Predicate<Account> predicate,String errorMsg){
        if (!predicate.test(this)) throw new AccountingServiceException(errorMsg);
    }
    /**
     * 创建交易明细
     * @param transactionDate
     * @param transactionAmount
     * @param transactionType
     */
    protected void createTransactionDetails(Date transactionDate, BigDecimal transactionAmount, String transactionType){
        Preconditions.checkNotNull(transactionAmount,"交易金额为空");
        Preconditions.checkNotNull(transactionType,"交易类型为空");
        Supplier<AccountTransactionDetails> supplier = AccountTransactionDetails::new;
        AccountTransactionDetails details = supplier.get();
        details.accountId(this.getAccountId())
                .transactionType(transactionType)
                .transactionAmount(transactionAmount)
                .transactionTime(transactionDate);
        Function<AccountTransactionDetails,AccountTransactionDetails> function = AccountTransactionDetails::new;
        this.getTransactionDetailses().add(function.apply(details));
    }

    /**
     * 更新账户的信用指数
     * @param amount
     * @param type
     */
    protected void updateCreditIndex(BigDecimal amount,String type){}
}
