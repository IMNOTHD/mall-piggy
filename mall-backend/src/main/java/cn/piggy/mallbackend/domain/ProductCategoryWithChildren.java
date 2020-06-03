package cn.piggy.mallbackend.domain;

import lombok.Data;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/3 14:13
 */
@Data
public class ProductCategoryWithChildren {
    /**
     * 对应id
     */
    private Long value;

    /**
     * 对应name
     */
    private String label;

    /**
     * 子菜单
     */
    List<ProductCategoryWithChildren> children;
}
