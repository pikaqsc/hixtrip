package com.hixtrip.sample.client.sample.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description TODO
 * @Author qyc
 * @Date 2024-03-15 16:01
 **/
public class OrderVO {
    /**
     * 订单号
     */
    private String id;

    /**
     * 购买人
     */
    private String userId;

    /**
     * SkuId
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品规格1
     */
    private String sku1;

    /**
     * 商品规格2
     */
    private String sku2;

    /**
     * 商品规格3
     */
    private String sku3;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
