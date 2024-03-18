package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.config.RedissonConfig;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.app.pay.PayServiceImpl;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.OrderVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.constants.KeyConstant;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public OrderVO order(CommandOderCreateDTO commandOderCreateDTO) throws Exception {
        String key = KeyConstant.InventoryKey + commandOderCreateDTO.getSkuId();
        RLock lock = redissonClient.getLock(key);
        if (lock.tryLock(RedissonConfig.waiTime, RedissonConfig.timeOutTime, TimeUnit.SECONDS)) {
            try {
                return placingOrder(commandOderCreateDTO);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        } else {
            throw new RuntimeException("系统异常");
        }
    }

    /**
     * 生成订单的过程
     * @param commandOderCreateDTO
     * @return
     */
    public OrderVO placingOrder(CommandOderCreateDTO commandOderCreateDTO) {
        String skuId = commandOderCreateDTO.getSkuId();
        Integer amount = commandOderCreateDTO.getAmount();
        if (!StringUtils.hasText(skuId)) {
            throw new RuntimeException("商品信息有误");
        }
        if (null == amount || amount.compareTo(0) == -1) {
            throw new RuntimeException("商品数量有误");
        }
        //获取库存，判断是否有库存进行扣减
        Inventory inventory = inventoryRepository.getInventory(skuId);
        long sellableQuantity = inventory.getSellableQuantity();
        if (sellableQuantity < amount.intValue()) {
            throw new RuntimeException("该款商品库存不足");
        }
        //扣减缓存中的库存(预占库存)
        Boolean blChange = inventoryRepository.changeInventory(skuId, sellableQuantity - amount.intValue(), inventory.getWithholdingQuantity() + amount.intValue(), inventory.getOccupiedQuantity());
        if (null == blChange || !blChange) {
            throw new RuntimeException("库存操作失败");
        }
        Order order;
        try {
            //生成订单
            BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
            BigDecimal money = skuPrice.multiply(new BigDecimal(amount.intValue()));
            //todo 生成订单号的公共方法
            order = Order.builder().id("按公共类方法生成的顶顶那id")
                    .skuId(skuId)
                    .amount(amount)
                    .userId(commandOderCreateDTO.getUserId())
                    .money(money)
                    .payStatus("0")
                    .build();
            int iCount = orderDomainService.createOrder(order);
            if (iCount <= 0) {
                throw new Exception("订单创建失败");
            }
        } catch (Exception ex) {
            //恢复预占用的库存,并抛出异常
            inventoryRepository.changeInventory(skuId, sellableQuantity, inventory.getWithholdingQuantity(), inventory.getOccupiedQuantity());
            throw new RuntimeException(ex.getMessage());
        }
        OrderVO orderVO = OrderConvertor.INSTANCE.convert(order);
        //todo 根据skuId获取商品信息，并返回给前端
        return orderVO;
    }

    /**
     * 第三方支付回调
     * @param commandPayDTO
     */
    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = PayConvertor.INSTANCE.convert(commandPayDTO);
        new PayServiceImpl(commandPayDTO.getPayStatus()).callback(commandPay);
    }
}
