package com.hixtrip.sample.app.pay;

import com.hixtrip.sample.app.pay.callback.PayDuplicate;
import com.hixtrip.sample.app.pay.callback.PayFail;
import com.hixtrip.sample.app.pay.callback.PayService;
import com.hixtrip.sample.app.pay.callback.PaySuccess;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @Description 支付回调处理，策略模式
 * @Author qyc
 * @Date 2024-03-16 13:23
 **/
public class PayServiceImpl extends PayService {

    PayService payService = null;

    public PayServiceImpl(String payStatus) {
        switch (payStatus) {
            case "支付成功":
                //todo 要判断是否重复支付
                boolean isDuplicate = false;
                if (isDuplicate) {
                    payService = new PayDuplicate();
                    break;
                }
                payService = new PaySuccess();
                break;
            case "支付失败":
                //要判断是否重复支付
                payService = new PayFail();
                break;
            case "重复支付":
                //要判断是否重复支付
                payService = new PayDuplicate();
                break;
        }
    }

    /**
     * 支付回调处理
     * @param commandPay
     */
    @Override
    public void callback(CommandPay commandPay) {
        payService.callback(commandPay);
    }
}
