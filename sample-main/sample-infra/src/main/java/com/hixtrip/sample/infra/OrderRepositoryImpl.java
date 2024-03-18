package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description TODO
 * @Author qyc
 * @Date 2024-03-15 18:42
 **/
@Component
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    final OrderMapper orderMapper;

    @Override
    public int createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.convert(order);
        order.setPayStatus("0");
        order.setDelFlag(0L);
        order.setCreateBy(order.getUserId());
        order.setCreateTime(LocalDateTime.now());
        return orderMapper.insert(orderDO);
    }

    @Override
    public int orderPayStatus(Order order) {
        LambdaUpdateWrapper<OrderDO> updateWrapper = Wrappers.<OrderDO>lambdaUpdate()
                .set(OrderDO::getPayStatus, order.getPayStatus())
                .set(OrderDO::getUpdateTime, LocalDateTime.now())
                .eq(OrderDO::getId, order.getId())
                .ne(OrderDO::getPayStatus, "1");
        return orderMapper.update(null, updateWrapper);
    }
}
