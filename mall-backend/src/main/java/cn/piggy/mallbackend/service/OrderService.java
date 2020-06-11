package cn.piggy.mallbackend.service;

import cn.piggy.mallbackend.domain.Order;
import cn.piggy.mallbackend.domain.OrderItem;
import cn.piggy.mallbackend.domain.OrderParam;
import cn.piggy.mallbackend.domain.ProductSnapshot;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/10 11:37
 */
public interface OrderService {
    @Transactional(rollbackFor = Exception.class)
    List<String> add(OrderParam orderParam, String username);

    Order info(String orderSn);

    List<OrderItem> detail(String orderSn);

    ProductSnapshot snapshot(Long id);

    List<Order> memberList(String username);

    List<Order> adminList(String username);
}
