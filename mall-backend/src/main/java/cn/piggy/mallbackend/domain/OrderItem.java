package cn.piggy.mallbackend.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * order_item
 * @author 
 */
@Data
public class OrderItem implements Serializable {
    private Integer id;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 商品数量
     */
    private Integer selectedNum;

    /**
     * 商品快照id
     */
    private Long snapshotId;

    /**
     * 商品sn（实际以快照为准）
     */
    private String productSn;

    private static final long serialVersionUID = 1L;
}