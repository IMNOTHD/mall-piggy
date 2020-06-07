package cn.piggy.mallbackend.controller;

import cn.piggy.mallbackend.common.api.CommonResult;
import cn.piggy.mallbackend.domain.Member;
import cn.piggy.mallbackend.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会员登录注册Controller
 *
 * @author IMNOTHD
 * @date 2020/5/21 0:07
 */
@Controller
@Api(tags = "MemberController", description = "会员登录注册管理")
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation("会员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String account, @RequestParam String password, HttpServletResponse httpServletResponse) {
        String token = memberService.login(account, password);
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        Cookie cookie = new Cookie("member_token", token);
        cookie.setPath("/");
        // 设置30d的过期时间
        cookie.setMaxAge(60 * 60 * 24 * 30);
        httpServletResponse.addCookie(cookie);
        return CommonResult.success();
    }

    @ApiOperation("会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String username, @RequestParam String phone, @RequestParam String password) {
        memberService.register(username, phone, password);
        return CommonResult.success("注册成功");
    }

    @ApiOperation("会员信息获取")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("获取用户信息失败，请检查登录状态");
        }

        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                Member member = memberService.info(cookie.getValue());
                if (member == null) {
                    return CommonResult.validateFailed("获取用户信息失败");
                } else {
                    return CommonResult.success(member);
                }
            }
        }
        return CommonResult.validateFailed("获取用户信息失败，请检查登录状态");
    }

    @ApiOperation("用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
        }
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                memberService.logout(cookie.getValue());
            }
        }
        return CommonResult.validateFailed("登出成功");
    }
}
