package cn.piggy.mallbackend.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * member_address
 * @author IMNOTHD
 */
@Data
public class MemberAddress implements Serializable {
    private Long id;

    private Integer memberId;

    /**
     * 收货人名称
     */
    private String name;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 是否默认, 0 -> false, 1 -> true
     */
    private Integer defaultStatus;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 省份/直辖市
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址
     */
    private String detailAddress;

    private static final long serialVersionUID = 1L;
}