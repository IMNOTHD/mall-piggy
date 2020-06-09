package cn.piggy.mallbackend.service;

import cn.piggy.mallbackend.domain.Member;
import cn.piggy.mallbackend.domain.MemberAddress;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/7 18:13
 */
public interface MemberAddressService {
    List<MemberAddress> getMemberAddressList(String username);

    void createAddress(MemberAddress memberAddress, String username);

    MemberAddress getAddressFromId(Long id);

    void updateAddress(MemberAddress memberAddress, String username);

    void deleteAddress(Long id);
}
