package cn.piggy.mallbackend.service.impl;

import cn.piggy.mallbackend.common.exception.Asserts;
import cn.piggy.mallbackend.dao.MemberAddressDao;
import cn.piggy.mallbackend.dao.MemberDao;
import cn.piggy.mallbackend.domain.Member;
import cn.piggy.mallbackend.domain.MemberAddress;
import cn.piggy.mallbackend.service.MemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/7 18:13
 */
@Service
public class MemberAddressServiceImpl implements MemberAddressService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberAddressDao memberAddressDao;

    @Override
    public List<MemberAddress> getMemberAddressList(String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Member member = memberDao.selectByUsernameOrPhone(username, username);

        return memberAddressDao.selectByMemberId(member.getId());
    }

    @Override
    public void createAddress(MemberAddress memberAddress, String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }
        Member member = memberDao.selectByUsernameOrPhone(username, username);
        memberAddress.setMemberId(member.getId());

        if (memberAddress.getDefaultStatus() == 1) {
            memberAddressDao.cleanDefaultStatusByMemberId(member.getId());
        }
        memberAddressDao.insert(memberAddress);
    }

    @Override
    public MemberAddress getAddressFromId(Long id) {
        return memberAddressDao.selectByPrimaryKey(id);
    }

    @Override
    public void updateAddress(MemberAddress memberAddress, String username) {
        Member member = memberDao.selectByUsernameOrPhone(username, username);
        memberAddress.setMemberId(member.getId());
        if (memberAddress.getDefaultStatus() == 1) {
            memberAddressDao.cleanDefaultStatusByMemberId(memberAddress.getMemberId());
        }
        memberAddressDao.updateByPrimaryKeySelective(memberAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        memberAddressDao.deleteByPrimaryKey(id);
    }
}
