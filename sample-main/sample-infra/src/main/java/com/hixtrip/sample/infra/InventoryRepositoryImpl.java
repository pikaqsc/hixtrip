package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.constants.KeyConstant;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Inventory getInventory(String skuId) {
        Inventory inventory = null;
        try {
            inventory = (Inventory) redisTemplate.opsForValue().get(KeyConstant.InventoryKey + skuId);
        } catch (Exception ex) {}

        if (null == inventory) {
            // todo 查询数据库，并把查询到的库存写入redis缓存，且作为返回值
        }
        return inventory;
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    @Override
    public Boolean changeInventory(String skuId, long sellableQuantity, long withholdingQuantity, long occupiedQuantity) {
        Inventory inventory = new Inventory();
        inventory.setSellableQuantity(sellableQuantity);
        inventory.setWithholdingQuantity(withholdingQuantity);
        inventory.setOccupiedQuantity(occupiedQuantity);
        redisTemplate.opsForValue().set(KeyConstant.InventoryKey + skuId, inventory);
        return null;
    }
}
