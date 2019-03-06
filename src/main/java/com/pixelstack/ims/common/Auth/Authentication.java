package com.pixelstack.ims.common.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pixelstack.ims.common.Redis.RedisOperator;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Component
@EnableScheduling   // 1.开启定时任务, 用于解除封印
@EnableAsync        // 2.开启多线程
public class Authentication {

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    UserMapper userMapper;

    /**
     * 生成 token
     * @param user
     * @return
     */
    public String createToken(User user) {
        String token = "";
        try {
            token = JWT.create()
                    .withAudience(String.valueOf(user.getUid()))          // 将 user id 保存到 token 里面
                    .sign(Algorithm.HMAC256(user.getPassword()));         // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }

    /**
     * 保存 token 至 redis 中
     * @param token
     * @param uid
     * @return
     */
    public boolean storeToken(String token, int uid) {

        try {
            redisOperator.select(2);
            redisOperator.set(token, String.valueOf(uid));
            redisOperator.expire(token, 3600 * 24);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 验证用户 token 是否存在以及是否有效
     * @param token
     * @return
     */
    public boolean vaildToken(String token) {
        redisOperator.select(2);
        return redisOperator.exit(token) && redisOperator.ttl(token) > 0? true:false;
    }

    /**
     * 根据 token 获取用户 id
     * @param token
     * @return
     */
    public String getUidByToken(String token) {
        return redisOperator.get(token);
    }

    /**
     * deleteToken
     * @param user
     */
    public void deleteToken(User user) {
        String oldToken = this.createToken(user);
        this.vaildToken(oldToken);
        redisOperator.del(oldToken);
    }

    @Async
    @Scheduled(fixedDelay = 1800000)  //间隔1秒 * 60 * 30 * 1000
    public void UnblockCount() throws InterruptedException {
        try {
                userMapper.unBlock();
                System.out.println("解封");
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
