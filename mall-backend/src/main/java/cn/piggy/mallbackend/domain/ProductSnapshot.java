package cn.piggy.mallbackend.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * product_snapshot
 * @author 
 */
@Data
public class ProductSnapshot implements Serializable {
    private Long id;

    /**
     * 商品分类id
     */
    private Long productCategoryId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 图片json list
     */
    private String pic;

    /**
     * 价格，存储的价格为实际价格*100，解决精度问题
     */
    private Integer price;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 货号（不做sku了，我要累死了）
     */
    private String productSn;

    /**
     * 归属商家id
     */
    private Integer adminId;

    private static final long serialVersionUID = 1L;
}