package cn.piggy.mallbackend.component;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * @author IMNOTHD
 * @date 2020/5/23 2:58
 */
@Component
public class CookieComponent {
    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.expire.common}")
    private Long redisExpire;
    @Value("${redis.key.member}")
    private String redisKeyMember;
    @Value("${redis.key.admin}")
    private String redisKeyAdmin;

    /**
     * 根据username生成token, 并存入Redis中
     *
     * @param userType userType
     * @param username username
     * @return token
     */
    public String addCookie(UserType userType, String username) {
        String token = UUID.randomUUID().toString();
        String key = redisDatabase + ":" + checkUserType(userType) + ":" + token;
        redisService.set(key, username, redisExpire);
        return token;
    }

    /**
     * 根据token获取username
     *
     * @param userType userType
     * @param token token
     * @return username
     */
    public String getUsername(UserType userType, String token) {
        String key = redisDatabase + ":" + checkUserType(userType) + ":" + token;
        return (String) redisService.get(key);
    }

    /**
     * 获得userType
     *
     * @param userType userType
     * @return redisUserType
     */
    private String checkUserType(UserType userType) {
        if (userType == UserType.ADMIN) {
            return redisKeyAdmin;
        } else {
            return redisKeyMember;
        }
    }
}
