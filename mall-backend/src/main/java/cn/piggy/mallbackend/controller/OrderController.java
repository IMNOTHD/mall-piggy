package cn.piggy.mallbackend.controller;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.common.api.CommonResult;
import cn.piggy.mallbackend.component.CookieComponent;
import cn.piggy.mallbackend.domain.OrderParam;
import cn.piggy.mallbackend.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/10 11:37
 */
@Controller
@Api(tags = "OrderController", description = "订单")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CookieComponent cookieComponent;

    @ApiOperation("下单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody OrderParam orderParam, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return CommonResult.validateFailed("未登录");
        }

        String memberToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("member_token".equals(cookie.getName())) {
                memberToken = cookie.getValue();
            }
        }

        List<String> orderList = orderService.add(orderParam, cookieComponent.getUsername(UserType.MEMBER, memberToken));

        return CommonResult.success(new HashMap<String, Object>() {{
            put("orderSnList", orderList);
        }});
    }

    @ApiOperation("查询订单信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info(@RequestParam String orderSn) {
        return CommonResult.success(orderService.info(orderSn));
    }

    @ApiOperation("查询订单内商品")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult detail(@RequestParam String orderSn) {
        return CommonResult.success(orderService.detail(orderSn));
    }

    @ApiOperation("查询单个商品快照")
    @RequestMapping(value = "/snapshot", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult snapshot(@RequestParam Long id) {
        return CommonResult.success(orderService.snapshot(id));
    }
}
