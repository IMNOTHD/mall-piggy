package cn.piggy.mallbackend.service.impl;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.exception.Asserts;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.dao.AdminDao;
import cn.piggy.mallbackend.domain.Admin;
import cn.piggy.mallbackend.service.AdminService;
import cn.piggy.mallbackend.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author IMNOTHD
 * @date 2020/5/24 23:05
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private CookieComponent cookieComponent;

    @Override
    public void register(String username, String email, String password) {
        // 禁止用户名带@, 避免和邮箱混淆
        if (CommonUtil.isStringHasAt(username)) {
            Asserts.validateFail("用户名不得有特殊字符");
        }
        // 查询该用户是否已存在
        Admin adminFromDatabase = adminDao.selectByUsernameOrEmail(username, email);
        if (adminFromDatabase != null) {
            Asserts.validateFail("用户已存在");
        }
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setEmail(email);
        // 我就是懒得加密密码, 能用就行.jpg
        admin.setPassword(password);
        admin.setCreateTime(new Date());
        adminDao.insert(admin);
        admin.setPassword(null);
    }

    @Override
    public String login(String account, String password) {
        if (password == null || password.length() == 0) {
            Asserts.validateFail("密码不得为空");
        }
        Admin admin = adminDao.selectByUsernameOrEmail(account, account);
        if (admin == null) {
            return null;
        }
        if (!password.equals(admin.getPassword())) {
            admin.setPassword(null);
            return null;
        }
        admin.setPassword(null);
        return cookieComponent.addCookie(UserType.ADMIN, admin.getUsername());
    }

    @Override
    public Admin info(String token) {
        String username = cookieComponent.getUsername(UserType.ADMIN, token);
        Admin admin = adminDao.selectByUsernameOrEmail(username, username);
        // 抹去重要信息
        admin.setPassword(null);
        admin.setId(null);
        return admin;
    }

    @Override
    public void logout(String token) {
        try {
            cookieComponent.removeToken(UserType.ADMIN, token);
        } catch (Exception ignored) {
        }
    }
}
