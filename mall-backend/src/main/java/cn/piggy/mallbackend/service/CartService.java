package cn.piggy.mallbackend.service;

import cn.piggy.mallbackend.domain.CartItem;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/6/9 20:21
 */
public interface CartService {
    void add(CartItem cartItem, String username);

    void delete(CartItem cartItem, String username);

    void setNum(CartItem cartItem, String username);

    List<CartItem> list(String username);
}
