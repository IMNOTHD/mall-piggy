package cn.piggy.mallbackend.dao;

import cn.piggy.mallbackend.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * pageOffset计算公式为pageSize * (pageIndex - 1), page从1开始
     * @param adminId 查询的admin的id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    List<Product> selectByAdminId(@Param("adminId") int adminId, @Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize);

    int countByAdminId(@Param("adminId") int adminId);
}