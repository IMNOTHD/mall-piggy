package cn.piggy.mallbackend.service;

import cn.piggy.mallbackend.domain.Admin;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author IMNOTHD
 * @date 2020/5/24 23:04
 */
public interface AdminService {
    @Transactional(rollbackFor = Exception.class)
    void register(String username, String email, String password);

    String login(String account, String password);

    Admin info(String token);

    void logout(String token);
}
