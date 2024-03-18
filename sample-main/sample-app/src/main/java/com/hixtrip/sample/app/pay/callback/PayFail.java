package com.hixtrip.sample.app.pay.callback;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 支付失败
 * @Author qyc
 * @Date 2024-03-16 13:13
 **/
public class PayFail extends PayService {

    @Autowired
    OrderDomainService orderDomainService;

    @Autowired
    PayDomainService payDomainService;
    /**
     * 支付失败回调
     * @param commandPay
     */
    @Override
    public void callback(CommandPay commandPay) {
        //变更订单支付状态为失败？
        int iCount = orderDomainService.orderPaySuccess(commandPay);
        if (iCount <= 0) {
            throw new RuntimeException("订单状态变更失败");
        }
        //新增支付记录
        payDomainService.payRecord(commandPay);
    }
}
