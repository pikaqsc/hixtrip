package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
@AllArgsConstructor
public class OrderDomainService {

    final OrderRepository orderRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public int createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        return orderRepository.createOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public int orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = Order.builder()
                .id(commandPay.getOrderId())
                .payStatus("1")
                .build();
        return orderRepository.orderPayStatus(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public int orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = Order.builder()
                .id(commandPay.getOrderId())
                .payStatus("2")
                .build();
        return orderRepository.orderPayStatus(order);
    }
}
