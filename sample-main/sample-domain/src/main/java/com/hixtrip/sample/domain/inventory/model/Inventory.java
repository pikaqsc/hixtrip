package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Description sku库存
 * @Author qyc
 * @Date 2024-03-15 21:07
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {
    /**
     * 可售库存
     */
    private long sellableQuantity;
    /**
     * 预占库存
     */
    private long withholdingQuantity;
    /**
     * 占用库存
     */
    private long occupiedQuantity;
}
