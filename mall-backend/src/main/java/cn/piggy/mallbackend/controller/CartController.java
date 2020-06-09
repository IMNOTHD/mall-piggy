package cn.piggy.mallbackend.controller;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.api.CommonResult;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.domain.CartItem;
import cn.piggy.mallbackend.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/8 7:39
 */
@Controller
@Api(tags = "CartController", description = "购物车管理")
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CookieComponent cookieComponent;

    @ApiOperation("购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CartItem>> list(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
        }

        String memberToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                memberToken = cookie.getValue();
            }
        }

        List<CartItem> cartItemList = cartService.list(cookieComponent.getUsername(UserType.MEMBER, memberToken));

        return CommonResult.success(cartItemList);
    }

    @ApiOperation("添加购物车商品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody CartItem cartItem, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
        }

        String memberToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                memberToken = cookie.getValue();
            }
        }

        cartService.add(cartItem, cookieComponent.getUsername(UserType.MEMBER, memberToken));

        return CommonResult.success("添加成功");
    }

    @ApiOperation("设置购物车商品数量")
    @RequestMapping(value = "/setNum", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult setNum(@RequestBody CartItem cartItem, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
        }

        String memberToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                memberToken = cookie.getValue();
            }
        }

        cartService.setNum(cartItem, cookieComponent.getUsername(UserType.MEMBER, memberToken));

        return CommonResult.success();
    }

    @ApiOperation("删除购物车商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestBody CartItem cartItem, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
        }

        String memberToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                memberToken = cookie.getValue();
            }
        }

        cartService.delete(cartItem, cookieComponent.getUsername(UserType.MEMBER, memberToken));

        return CommonResult.success("删除成功");
    }
}
