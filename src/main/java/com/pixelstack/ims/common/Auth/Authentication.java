package com.pixelstack.ims.common.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pixelstack.ims.common.Redis.RedisOperator;
import com.pixelstack.ims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
public class Authentication {

    @Autowired
    RedisOperator redisOperator;

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
        redisOperator.del(oldToken);
    }
}
