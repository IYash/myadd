package com.person.design.service;

import com.person.design.exception.AccountingServiceException;

import java.math.BigDecimal;

/**
 * Created by huangchangling on 2018/11/13.
 * 账户交易服务接口
 */
public interface AccountingService {
    /**
     * @param srcAccountId
     * @param destAccountId
     * @param amount
     * @throws AccountingServiceException
     */
    void transfer(Long srcAccountId,Long destAccountId,BigDecimal amount) throws AccountingServiceException;

}
