package cn.piggy.mallbackend.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order
 * @author 
 */
@Data
public class Order implements Serializable {
    private Long id;

    /**
     * 订单member_id
     */
    private Integer memberId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 提交时间
     */
    private Date createTime;

    /**
     * 订单总金额
     */
    private Long price;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人手机号
     */
    private String receiverPhone;

    /**
     * 0->已提交, 未付款; 1->已付款, 未发货; 2->已发货, 未收货; 3->已收货, 未评价; 4->已评价
     */
    private Integer status;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 物流单号
     */
    private String deliverySn;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 收货时间
     */
    private Date confirmTime;

    /**
     * 商家id
     */
    private Integer adminId;

    private static final long serialVersionUID = 1L;
}