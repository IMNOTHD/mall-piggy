package cn.piggy.mallbackend.service.impl;

import cn.piggy.mallbackend.common.exception.Asserts;
import cn.piggy.mallbackend.dao.AdminDao;
import cn.piggy.mallbackend.dao.ProductCategoryDao;
import cn.piggy.mallbackend.dao.ProductDao;
import cn.piggy.mallbackend.domain.*;
import cn.piggy.mallbackend.service.ProductService;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * @author IMNOTHD
 * @date 2020/6/2 17:44
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public String imageUpload(MultipartFile multipartFile) {
        final String pictureBaseUrl = "https://sm.ms/api/v2/upload";
        final String authorization = "ZpNuYEYhRaEPYrCgpRFaj1PeRLG0uEa6";
        String result = "";

        OkHttpClient client = new OkHttpClient();
        File file = null;
        try {
            file = File.createTempFile("tmp", null);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("ssl", "true")
                .addFormDataPart("smfile", multipartFile.getOriginalFilename(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(pictureBaseUrl)
                .header("Authorization", authorization)
                .header("contentType", "multipart/form-data")
                .header("User-Agent",  "PicGo")
                .post(multipartBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                response.body().close();
                Asserts.fail("上传图片失败");
            }
            response.body().close();
        } catch (Exception e) {
            Asserts.fail("上传图片失败");
        }

        return result;
    }

    @Override
    public List<ProductCategoryWithChildren> showAllCategory() {
        return productCategoryDao.listWithChildren();
    }

    @Override
    public void create(ProductCreate productCreate, String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Product product = new Product();
        product.setName(productCreate.getName());
        product.setDescription(productCreate.getDescription());
        product.setPublishStatus(productCreate.isPublishStatus() ? 1 : 0);
        product.setProductCategoryId(productCreate.getProductCategoryId()[productCreate.getProductCategoryId().length - 1]);
        product.setStock(productCreate.getStock());
        product.setPrice(productCreate.getPrice());
        JSONObject jsonObject = (JSONObject) JSONObject.parse(JSONObject.toJSONString(productCreate.getUpload()));
        product.setPic(jsonObject.get("fileList").toString());
        Admin admin = adminDao.selectByUsernameOrEmail(username, username);
        product.setAdminId(admin.getId());
        product.setSale(0);
        product.setProductSn(UUID.randomUUID().toString());

        int count = productDao.insert(product);
        if (count == 0) {
            Asserts.fail("添加商品失败，请重试");
        }
    }

    @Override
    public List<Product> getProductByAdmin(int page, int pageSize, String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Admin admin = adminDao.selectByUsernameOrEmail(username, username);
        return productDao.selectByAdminId(admin.getId(), pageSize * (page - 1), pageSize);
    }

    @Override
    public int countByAdmin(String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Admin admin = adminDao.selectByUsernameOrEmail(username, username);
        return productDao.countByAdminId(admin.getId());
    }

    @Override
    public void changePublish(String productSn) {
        productDao.changePublishStatus(productSn);
    }

    @Override
    public void changeStock(String productSn, int stock) {
        productDao.changeStock(productSn, stock);
    }

    @Override
    public void deleteProduct(String productSn) {
        productDao.deleteByProductSn(productSn);
    }
}
