package cn.piggy.mallbackend.domain;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * @author IMNOTHD
 * @date 2020/6/3 17:05
 */
@Data
public class ProductCreate {
    private String name;

    private String description;

    private boolean publishStatus;

    private Long[] productCategoryId;

    private int stock;

    private Object upload;

    /**
     * 价格, 为避免精度问题, 存储的是*100后的价格
     */
    private int price;
}
