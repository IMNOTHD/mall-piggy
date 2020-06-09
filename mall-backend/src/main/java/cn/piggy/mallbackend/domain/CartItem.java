package cn.piggy.mallbackend.domain;

import lombok.Data;

/**
 * @author IMNOTHD
 * @date 2020/6/10 0:46
 */
@Data
public class CartItem {
    private String productSn;

    private int selectedNum;
}
