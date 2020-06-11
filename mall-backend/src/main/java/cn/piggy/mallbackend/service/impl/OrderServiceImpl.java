package cn.piggy.mallbackend.service.impl;

import cn.piggy.mallbackend.common.exception.Asserts;
import cn.piggy.mallbackend.dao.*;
import cn.piggy.mallbackend.domain.*;
import cn.piggy.mallbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author IMNOTHD
 * @date 2020/6/10 11:38
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductSnapshotDao productSnapshotDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private AdminDao adminDao;

    @Override
    public List<String> add(OrderParam orderParam, String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Member member = memberDao.selectByUsernameOrPhone(username, username);

        Set<Integer> adminList = new HashSet<>();
        Map<Integer, String> adminToOrderMap = new HashMap<>();
        Map<String, Long> orderPriceMap = new HashMap<>();
        for (OrderItem orderItem : orderParam.getOrderItems()) {
            // 减库存, 失败则抛出异常, 事务回滚
            Product product = productDao.selectByProductSn(orderItem.getProductSn());
            if (product.getStock() < orderItem.getSelectedNum()) {
                Asserts.fail("库存不足");
            }
            int count = productDao.changeStock(orderItem.getProductSn(), product.getStock() - orderItem.getSelectedNum());
            if (count == 0) {
                Asserts.fail("库存不足");
            }

            // 对每个admin新建order
            Order order = new Order();
            if (!adminList.contains(product.getAdminId())) {
                order.setAdminId(product.getAdminId());
                order.setCreateTime(new Date());
                order.setMemberId(member.getId());
                order.setOrderSn(UUID.randomUUID().toString());
                order.setReceiverAddress(orderParam.getReceiverAddress());
                order.setReceiverName(orderParam.getReceiverName());
                order.setReceiverPhone(orderParam.getReceiverPhone());
                order.setStatus(0);
                orderDao.insert(order);
                adminToOrderMap.put(product.getAdminId(), order.getOrderSn());
                adminList.add(product.getAdminId());
            }

            // 打快照
            ProductSnapshot productSnapshot = new ProductSnapshot();
            productSnapshot.setAdminId(product.getAdminId());
            productSnapshot.setDescription(product.getDescription());
            productSnapshot.setName(product.getName());
            productSnapshot.setPic(product.getPic());
            productSnapshot.setPrice(product.getPrice());
            productSnapshot.setProductCategoryId(product.getProductCategoryId());
            productSnapshot.setProductSn(product.getProductSn());
            productSnapshotDao.insert(productSnapshot);
            String orderSn = adminToOrderMap.get(product.getAdminId());

            // 插入order_item表
            OrderItem orderItemInsert = new OrderItem();
            orderItemInsert.setSnapshotId(productSnapshot.getId());
            orderItemInsert.setOrderSn(orderSn);
            orderItemInsert.setSelectedNum(orderItem.getSelectedNum());
            orderItemDao.insert(orderItemInsert);
            if (orderPriceMap.containsKey(orderSn)) {
                orderPriceMap.put(orderSn, (long) (orderItem.getSelectedNum() * product.getPrice()) + orderPriceMap.get(orderSn));
            } else {
                orderPriceMap.put(orderSn, (long) (orderItem.getSelectedNum() * product.getPrice()));
            }
        }
        List<String> orderList = new ArrayList<>();
        orderPriceMap.forEach((key, value) -> {
            orderDao.changeOrderPrice(key, value);
            orderList.add(key);
        });

        return orderList;
    }

    @Override
    public Order info(String orderSn) {
        return orderDao.selectByOrderSn(orderSn);
    }

    @Override
    public List<OrderItem> detail(String orderSn) {
        return orderItemDao.selectByOrderSn(orderSn);
    }

    @Override
    public ProductSnapshot snapshot(Long id) {
        return productSnapshotDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> memberList(String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Member member = memberDao.selectByUsernameOrPhone(username, username);

        return orderDao.selectByMemberId(member.getId());
    }

    @Override
    public List<Order> adminList(String username) {
        if (username == null) {
            Asserts.validateFail("登录状态错误，请重新登录");
        }

        Admin admin = adminDao.selectByUsernameOrEmail(username, username);

        return orderDao.selectByAdminId(admin.getId());
    }
}
