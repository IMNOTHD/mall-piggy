package cn.piggy.mallbackend.dao;

import cn.piggy.mallbackend.domain.ProductSnapshot;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProductSnapshotDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSnapshot record);

    int insertSelective(ProductSnapshot record);

    ProductSnapshot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSnapshot record);

    int updateByPrimaryKey(ProductSnapshot record);
}