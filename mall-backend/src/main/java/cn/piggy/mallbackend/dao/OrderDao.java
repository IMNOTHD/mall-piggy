package cn.piggy.mallbackend.dao;

import cn.piggy.mallbackend.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderSn(String orderSn);

    int changeOrderPrice(@Param("orderSn") String orderSn, @Param("price") Long price);

    List<Order> selectByMemberId(Integer memberId);

    List<Order> selectByAdminId(Integer adminId);
}