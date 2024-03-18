package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {

    /**
     * 创建待支付订单
     * @param order
     * @return
     */
    int createOrder(Order order);

    /**
     * 改变订单支付状态
     * @param order
     * @return
     */
    int orderPayStatus(Order order);
}
