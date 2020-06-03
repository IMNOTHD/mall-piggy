package cn.piggy.mallbackend.controller;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.api.CommonResult;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.domain.ProductCategory;
import cn.piggy.mallbackend.domain.ProductCategoryWithChildren;
import cn.piggy.mallbackend.domain.ProductCreate;
import cn.piggy.mallbackend.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/1 23:59
 */
@Controller
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CookieComponent cookieComponent;

    @ApiOperation("创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody ProductCreate productCreate, HttpServletRequest httpServletRequest) {
        String adminToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminToken = cookie.getValue();
            }
        }
        if (adminToken == null) {
            return CommonResult.failed("未登录");
        }
        productService.create(productCreate, cookieComponent.getUsername(UserType.ADMIN, adminToken));
        return CommonResult.success();
    }

    @ApiOperation("上传图片")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        String result = productService.imageUpload(multipartFile);
        return result;
    }

    @ApiOperation("查询分类")
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ProductCategoryWithChildren>> category() {
        List<ProductCategoryWithChildren> productCategoryWithChildrenList = productService.showAllCategory();
        if (productCategoryWithChildrenList == null) {
            return CommonResult.failed("查询出错，请重试");
        }
        return CommonResult.success(productCategoryWithChildrenList);
    }
}
