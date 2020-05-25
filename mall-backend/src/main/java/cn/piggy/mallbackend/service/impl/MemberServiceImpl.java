package cn.piggy.mallbackend.service.impl;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.exception.Asserts;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.dao.MemberDao;
import cn.piggy.mallbackend.domain.Member;
import cn.piggy.mallbackend.service.MemberService;
import cn.piggy.mallbackend.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员登录注册Service实现类
 *
 * @author IMNOTHD
 * @date 2020/5/21 0:44
 */
@Service
public class MemberServiceImpl implements MemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CookieComponent cookieComponent;

    @Override
    public void register(String username, String phone, String password) {
        // 禁止用户名使用纯数字, 避免和手机号混淆
        if (CommonUtil.isNumberOnly(username)) {
            Asserts.validateFail("用户名不得为纯数字");
        }
        // 查询该用户是否已存在
        Member memberFromDatabase = memberDao.selectByUsernameOrPhone(username, phone);
        if (memberFromDatabase != null) {
            Asserts.validateFail("用户已存在");
        }
        // 用户不存在, 注册用户
        Member member = new Member();
        member.setUsername(username);
        member.setPhone(phone);
        // 没错, 我就是懒的加密密码
        member.setPassword(password);
        member.setCreateTime(new Date());
        member.setGender((byte) 0);
        memberDao.insert(member);
        member.setPassword(null);
    }

    @Override
    public String login(String account, String password) {
        if (password == null || password.length() == 0) {
            Asserts.validateFail("密码不得为空");
        }
        Member member = memberDao.selectByUsernameOrPhone(account, account);
        if (member == null) {
            return null;
        }
        if (!password.equals(member.getPassword())) {
            member.setPassword(null);
            return null;
        }
        member.setPassword(null);
        return cookieComponent.addCookie(UserType.MEMBER, member.getUsername());
    }

    @Override
    public Member info(String token) {
        String username = cookieComponent.getUsername(UserType.MEMBER, token);
        Member member = memberDao.selectByUsernameOrPhone(username, username);
        // 抹去重要信息
        member.setPassword(null);
        member.setId(null);
        return member;
    }
}
