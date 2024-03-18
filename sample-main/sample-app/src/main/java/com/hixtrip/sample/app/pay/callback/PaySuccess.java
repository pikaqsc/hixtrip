package com.hixtrip.sample.app.pay.callback;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 支付成功
 * @Author qyc
 * @Date 2024-03-16 13:09
 **/
public class PaySuccess extends PayService {

    @Autowired
    OrderDomainService orderDomainService;

    @Autowired
    PayDomainService payDomainService;

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * 支付成功回调
     * @param commandPay
     */
    @Override
    public void callback(CommandPay commandPay) {
        int iCount = orderDomainService.orderPaySuccess(commandPay);
        if (iCount <= 0) {
            throw new RuntimeException("订单状态变更失败");
        }
        //新增支付记录
        payDomainService.payRecord(commandPay);
        //todo 根据订单号获取订单的商品数量、skuId
        OrderDO orderDO = OrderDO.builder()
                .skuId("kunycv987")
                .amount(2)
                .build();
        String skuId = orderDO.getSkuId();
        Integer amount = orderDO.getAmount();
        //变更缓存中的库存
        Inventory inventory = inventoryRepository.getInventory(skuId);
        long withholdingQuantity = inventory.getWithholdingQuantity();
        if (withholdingQuantity < amount.intValue()) {
            throw new RuntimeException("库存异常");
        }
        //占用库存增加
        Boolean blChange = inventoryRepository.changeInventory(skuId, inventory.getSellableQuantity(), withholdingQuantity - amount.intValue(), inventory.getOccupiedQuantity() + amount.intValue());
        if (null == blChange || !blChange) {
            throw new RuntimeException("库存操作失败");
        }

    }
}
