package com.person.design.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huangchangling on 2018/11/14.
 * 账户交易明细，它从属于账户，每个账户有多个交易明细
 * 包括以下属性：对象标识，所属账户，交易类型，交易发生金额，交易发生时间
 */
@Data
public class AccountTransactionDetails implements Serializable{

    private static final long serialVersionUID = 1771105531886633463L;
    private Long id;
    private Account account;
    private String transactionType;
    private BigDecimal transactionAmount;
    private Date transactionTime;

    public AccountTransactionDetails() {
    }

    public AccountTransactionDetails(AccountTransactionDetails details) {
        BeanUtils.copyProperties(details,this);
    }

    static enum TRANSACTION_TYPE{
        RECEIPTS,EXPENDITURE
    }

    public AccountTransactionDetails account(Account account) {
        this.account = account;
        return this;
    }
    public AccountTransactionDetails transactionType(String transactionType){
        this.transactionType = transactionType;
        return this;
    }
    public AccountTransactionDetails transactionAmount(BigDecimal transactionAmount){
        this.transactionAmount = transactionAmount;
        return this;
    }
    public AccountTransactionDetails transactionTime(Date transactionTime){
        this.transactionTime = transactionTime;
        return this;
    }

}
