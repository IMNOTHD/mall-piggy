package cn.piggy.mallbackend.controller;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.api.CommonResult;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.domain.Admin;
import cn.piggy.mallbackend.service.AdminService;
import cn.piggy.mallbackend.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author IMNOTHD
 * @date 2020/5/24 23:03
 */
@Controller
@Api(tags = "MemberController", description = "商家登录注册管理")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CookieComponent cookieComponent;

    @ApiOperation("商家登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String account, @RequestParam String password, HttpServletResponse httpServletResponse) {
        String token = adminService.login(account, password);
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        Cookie cookie = new Cookie("admin_token", token);
        cookie.setPath("/");
        // 设置30d的过期时间
        cookie.setMaxAge(60 * 60 * 24 * 30);
        httpServletResponse.addCookie(cookie);
        // cookies跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        return CommonResult.success();
    }

    @ApiOperation("商家注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        adminService.register(username, email, password);
        return CommonResult.success("注册成功");
    }

    @ApiOperation("商家信息获取")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info(HttpServletRequest httpServletRequest) {
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                Admin admin = adminService.info(cookie.getValue());
                if (admin == null) {
                    return CommonResult.validateFailed("获取用户信息失败");
                } else {
                    return CommonResult.success(admin);
                }
            }
        }
        return CommonResult.validateFailed("获取用户信息失败，请检查登录状态");
    }

    @ApiOperation("商家登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout(HttpServletRequest httpServletRequest) {
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("admin_token".equals(cookie.getName())) {
                adminService.logout(cookie.getValue());
            }
        }
        return CommonResult.validateFailed("登出成功");
    }

    @ApiOperation("查询订单列表")
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult orderList(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
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

        return CommonResult.success(orderService.adminList(cookieComponent.getUsername(UserType.ADMIN, adminToken)));
    }
}
