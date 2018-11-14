package com.person.design.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangchangling on 2018/11/14.
 * 银行交易手续费用计算器，它包含一个常量：眉笔交易的手续费上限
 */
@Data
public class BankTransactionFeeCalculator {

    public static final BigDecimal MAX_FEE_AMOUNT = new BigDecimal(50);

    /**
     * 根据交易信息计算该笔交易的手续费
     * @param transactionAmount
     * @param transactionType
     * @return
     */
    public BigDecimal calculateTransactionFee(BigDecimal transactionAmount,String transactionType){
        return null;
    }
}
