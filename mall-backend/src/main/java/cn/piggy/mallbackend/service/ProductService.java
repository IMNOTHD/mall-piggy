package cn.piggy.mallbackend.service;

import cn.piggy.mallbackend.domain.Product;
import cn.piggy.mallbackend.domain.ProductCategory;
import cn.piggy.mallbackend.domain.ProductCategoryWithChildren;
import cn.piggy.mallbackend.domain.ProductCreate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/2 17:43
 */
public interface ProductService {
    String imageUpload(MultipartFile multipartFile);

    List<ProductCategoryWithChildren> showAllCategory();

    void create(ProductCreate productCreate, String username);

    List<Product> getProductByAdmin(int page, int pageSize, String username);

    int countByAdmin(String username);

    void changePublish(String productSn);

    void changeStock(String productSn, int stock);

    void deleteProduct(String productSn);
}
