package cn.piggy.mallbackend.dao;

import cn.piggy.mallbackend.domain.MemberAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberAddressDao {
    int deleteByPrimaryKey(Long id);

    int insert(MemberAddress record);

    int insertSelective(MemberAddress record);

    MemberAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberAddress record);

    int updateByPrimaryKey(MemberAddress record);

    List<MemberAddress> selectByMemberId(Integer memberId);

    void cleanDefaultStatusByMemberId(Integer memberId);
}