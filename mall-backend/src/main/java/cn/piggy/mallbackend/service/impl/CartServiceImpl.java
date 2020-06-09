package cn.piggy.mallbackend.service.impl;

import cn.piggy.mallbackend.domain.CartItem;
import cn.piggy.mallbackend.service.CartService;
import cn.piggy.mallbackend.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author IMNOTHD
 * @date 2020/6/9 20:21
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.key.cart}")
    private String redisKeyCart;

    @Override
    public void add(CartItem cartItem, String username) {
        // 使用hash存储
        String key = redisDatabase + ":" + username;
        boolean isProductInRedis = redisService.hashHasKey(key, cartItem.getProductSn());
        if (isProductInRedis) {
            int numInRedis = (int) redisService.hashGet(key, cartItem.getProductSn());
            numInRedis++;
            redisService.hashSet(key, cartItem.getProductSn(), numInRedis);
        } else {
            redisService.hashSet(key, cartItem.getProductSn(), 1);
        }
    }

    @Override
    public void delete(CartItem cartItem, String username) {
        String key = redisDatabase + ":" + username;
        boolean isProductInRedis = redisService.hashHasKey(key, cartItem.getProductSn());
        if (isProductInRedis) {
            redisService.hashDel(key, cartItem.getProductSn());
        }
    }

    @Override
    public void setNum(CartItem cartItem, String username) {
        String key = redisDatabase + ":" + username;
        redisService.hashSet(key, cartItem.getProductSn(), cartItem.getSelectedNum());
    }

    @Override
    public List<CartItem> list(String username) {
        String key = redisDatabase + ":" + username;
        Map<Object, Object> cartMap = redisService.hashGetAll(key);
        List<CartItem> cartItemList = new ArrayList<>();
        cartMap.forEach((productSn, selectedNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setProductSn((String) productSn);
            cartItem.setSelectedNum((Integer) selectedNum);
            cartItemList.add(cartItem);
        });
        return cartItemList;
    }
}
