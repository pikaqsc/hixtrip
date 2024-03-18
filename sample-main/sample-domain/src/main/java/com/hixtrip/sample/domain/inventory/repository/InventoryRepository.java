package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 获取sku的库存
     * @param skuId
     * @return
     */
    Inventory getInventory(String skuId);

    /**
     * 修改库存
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    Boolean changeInventory(String skuId, long sellableQuantity, long withholdingQuantity, long occupiedQuantity);
}
