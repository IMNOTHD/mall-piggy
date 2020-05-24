package cn.piggy.mallbackend.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member
 * @author IMNOTHD
 */
@Data
public class Member implements Serializable {
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 头像
     */
    private String icon;

    /**
     * 性别：0->未知；1->男；2->女
     */
    private Byte gender;

    /**
     * 生日
     */
    private Date birthday;

    private static final long serialVersionUID = 1L;
}