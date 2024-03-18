package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.OrderVO;
import com.hixtrip.sample.domain.common.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@AllArgsConstructor
@RestController
public class OrderController {

    final OrderService orderService;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public Response<OrderVO> order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        //登录信息可以在这里模拟 (获取当前登录userId)
        String userId = "";
        // 校验是否为当前用户？？
        if (!StringUtils.hasText(userId) || !userId.equals(commandOderCreateDTO.getUserId())) {
            Response.fail("订单请求非当前用户");
        }
        try {
            return Response.success(orderService.order(commandOderCreateDTO));
        } catch (Exception ex) {
            return Response.fail("下单失败：" + ex.getMessage().substring(0, 9));
        }
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public Response<String> payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        orderService.payCallback(commandPayDTO);
        return Response.success("记录成功");
    }

}
