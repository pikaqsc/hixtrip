package com.hixtrip.sample.app.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @Description TODO
 * @Author qyc
 * @Date 2024-03-16 13:03
 **/
public abstract class PayService {
    /**
     * 支付回调处理
     * @param commandPay
     */
    public abstract void callback(CommandPay commandPay);
}
