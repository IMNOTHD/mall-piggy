package cn.piggy.mallbackend.service;

import cn.piggy.mallbackend.domain.Member;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员注册登录Service
 *
 * @author IMNOTHD
 * @date 2020/5/21 0:31
 */
public interface MemberService {
    @Transactional(rollbackFor = Exception.class)
    void register(String username, String phone, String password);

    String login(String account, String password);

    Member info(String token);

    void logout(String token);
}
