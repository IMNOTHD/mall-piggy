package cn.piggy.mallbackend.controller;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.api.CommonResult;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.domain.Product;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author IMNOTHD
 * @date 2020/6/1 23:59
 */
@Controller
@Api(tags = "ProductController", description = "商品管理")
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

    @ApiOperation("删除商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam String productSn, HttpServletRequest httpServletRequest) {
        String adminToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminToken = cookie.getValue();
            }
        }
        if (adminToken == null) {
            return CommonResult.failed("未登录");
        }

        productService.deleteProduct(productSn);

        return CommonResult.success();
    }

    @ApiOperation("上传图片")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        String result = productService.imageUpload(multipartFile);
        return result;
    }

    @ApiOperation("查询子分类下商品")
    @RequestMapping(value = "/category/query", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult categoryById(Long id) {
        List<Product> productList = productService.showCategoryProduct(id);

        return CommonResult.success(productList);
    }

    @ApiOperation("查询子分类名字")
    @RequestMapping(value = "/category/queryName", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult categoryNameById(@RequestParam Long id) {
        String name = productService.showCategoryName(id);

        return CommonResult.success(name);
    }
    @ApiOperation("查询子分类名字")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult queryWithProductSn(@RequestParam String productSn) {
        Product product = productService.queryWithProductSn(productSn);

        return CommonResult.success(product);
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

    @ApiOperation("帐号下商品列表")
    @RequestMapping(value = "/manage/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult manage(@RequestParam int page, @RequestParam int pageSize, HttpServletRequest httpServletRequest) {
        if (page < 1) {
            return CommonResult.validateFailed();
        }

        String adminToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminToken = cookie.getValue();
            }
        }
        if (adminToken == null) {
            return CommonResult.failed("未登录");
        }

        List<Product> productList = productService.getProductByAdmin(page, pageSize, cookieComponent.getUsername(UserType.ADMIN, adminToken));

        return CommonResult.success(productList);
    }

    @ApiOperation("帐号下商品数量")
    @RequestMapping(value = "/manage/count", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult count(HttpServletRequest httpServletRequest) {
        String adminToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminToken = cookie.getValue();
            }
        }
        if (adminToken == null) {
            return CommonResult.failed("未登录");
        }

        int count = productService.countByAdmin(cookieComponent.getUsername(UserType.ADMIN, adminToken));

        return CommonResult.success(new HashMap<String, Integer>() {{
            put("count", count);
        }});
    }

    @ApiOperation("修改上架状态")
    @RequestMapping(value = "/manage/changePublish", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult changePublish(@RequestParam String productSn, HttpServletRequest httpServletRequest) {
        String adminToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminToken = cookie.getValue();
            }
        }
        if (adminToken == null) {
            return CommonResult.failed("未登录");
        }

        productService.changePublish(productSn);

        return CommonResult.success();
    }

    @ApiOperation("修改库存")
    @RequestMapping(value = "/manage/changeStock", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult changeStock(@RequestParam String productSn, @RequestParam int stock, HttpServletRequest httpServletRequest) {
        String adminToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminToken = cookie.getValue();
            }
        }
        if (adminToken == null) {
            return CommonResult.failed("未登录");
        }

        productService.changeStock(productSn, stock);

        return CommonResult.success();
    }
}
