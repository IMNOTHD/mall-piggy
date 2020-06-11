package cn.piggy.mallbackend.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/10 21:28
 */
@Data
public class OrderParam implements Serializable {
    private List<OrderItem> orderItems;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;
}
