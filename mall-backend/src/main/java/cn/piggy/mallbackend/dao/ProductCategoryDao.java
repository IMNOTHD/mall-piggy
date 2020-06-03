package cn.piggy.mallbackend.dao;

import cn.piggy.mallbackend.domain.ProductCategory;
import cn.piggy.mallbackend.domain.ProductCategoryWithChildren;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductCategoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> selectAllProductCategory();

    List<ProductCategoryWithChildren> listWithChildren();

    List<ProductCategoryWithChildren> findByParentId();
}