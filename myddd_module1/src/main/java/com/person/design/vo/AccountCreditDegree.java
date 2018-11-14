package com.person.design.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangchangling on 2018/11/14.
 * 信用等级,它用于限制账户的每笔交易发生金额，包含以下属性
 * 对象标识，对应账户，信用指数
 */
@Data
public class AccountCreditDegree {

    private Long id;
    private Account account;
    private Long creditIndex;

    /**
     * 获取所属账户的眉笔交易最大金额
     * @return
     */
    public BigDecimal getMaxTransactionAmount(){
        return null;
    }
}
