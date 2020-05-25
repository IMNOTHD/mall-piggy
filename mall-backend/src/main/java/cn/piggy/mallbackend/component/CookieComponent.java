package cn.piggy.mallbackend.component;

import cn.piggy.mallbackend.common.UserType;
import cn.piggy.mallbackend.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

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
        String key = redisDatabase + ":" + checkUserType(userType) + ":token:" + token;
        String setKey = redisDatabase + ":" + checkUserType(userType) + ":username:" + username;
        redisService.set(key, username, redisExpire);
        redisService.setAddWithExpireTime(setKey, redisExpire, token);
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
        String key = redisDatabase + ":" + checkUserType(userType) + ":token:" + token;
        return (String) redisService.get(key);
    }

    /**
     * 根据username删除所有对应的token
     *
     * @param userType userType
     * @param username username
     */
    public void removeAllToken(UserType userType, String username) {
        String setKey = redisDatabase + ":" + checkUserType(userType) + ":username:" + username;
        Set<Object> tokenSet = redisService.setMembers(setKey);
        for (Object token : tokenSet) {
            try {
                redisService.del(redisDatabase + ":" + checkUserType(userType) + ":token:" + token.toString());
            } catch (Exception ignored) {}
        }
        redisService.del(setKey);
    }

    /**
     * 删除一个token
     *
     * @param userType userType
     * @param token token
     */
    public void removeToken(UserType userType, String token) {
        String tokenKey = redisDatabase + ":" + checkUserType(userType) + ":token:" + token;
        String username = (String) redisService.get(tokenKey);
        String setKey = redisDatabase + ":" + checkUserType(userType) + ":username:" + username;
        redisService.setRemove(setKey, token);
        redisService.del(tokenKey);
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
