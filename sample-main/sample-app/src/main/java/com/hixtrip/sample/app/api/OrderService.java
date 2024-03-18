package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.OrderVO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 创建订单全流程
     * @param commandOderCreateDTO
     * @return
     */
    OrderVO order(CommandOderCreateDTO commandOderCreateDTO) throws Exception;

    /**
     * 第三方支付回调
     * @param commandPayDTO
     */
    void payCallback(CommandPayDTO commandPayDTO);
}
