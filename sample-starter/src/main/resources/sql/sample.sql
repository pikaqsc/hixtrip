#todo 你的建表语句,包含索引

CREATE TABLE order202403 (
   id varchar(36) NOT NULL COMMENT '订单号',
   user_id varchar(36) NOT NULL COMMENT '购买人',
   sku_id varchar(16) NOT NULL COMMENT 'SkuId',
   amount int(10) NOT NULL COMMENT '购买数量',
   money decimal(9,2) NOT NULL COMMENT '购买金额',
   pay_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
   pay_status varchar(4) DEFAULT 0 COMMENT '支付状态，0 待支付 1 已支付 2 支付失败',
   del_flag char(4) DEFAULT NULL COMMENT '删除标志，0 存在 1 删除',

   create_by varchar(64) DEFAULT NULL COMMENT '创建人',
   create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_by varchar(64) DEFAULT NULL COMMENT '修改人',
   update_time datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (id) USING BTREE,
   INDEX idx_user_id(user_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';

CREATE TABLE order202404 (
    like order202403
);
