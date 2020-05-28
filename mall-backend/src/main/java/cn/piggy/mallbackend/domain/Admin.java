package cn.piggy.mallbackend.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * admin
 * @author IMNOTHD
 */
@Data
public class Admin implements Serializable {
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
     * 邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 昵称
     */
    private String nickname;

    private static final long serialVersionUID = 1L;
}