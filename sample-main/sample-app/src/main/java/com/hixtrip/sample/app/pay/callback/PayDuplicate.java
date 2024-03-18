package com.hixtrip.sample.app.pay.callback;

import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 重复支付
 * @Author qyc
 * @Date 2024-03-16 13:09
 **/
public class PayDuplicate extends PayService {

    @Autowired
    PayDomainService payDomainService;
    /**
     * 重复支付回调
     * @param commandPay
     */
    @Override
    public void callback(CommandPay commandPay) {
        //新增支付记录
        payDomainService.payRecord(commandPay);
        //todo 消息等提醒订单存在重复支付的问题
    }
}
