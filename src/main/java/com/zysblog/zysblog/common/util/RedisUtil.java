package com.zysblog.zysblog.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component

public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /** -------------------key相关操作--------------------- */

    /**
     * 删除key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key
     * @return
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }
    /** -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取字符串的长度
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @param increment
     * @return
     */
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key
     * @param value
     * @return
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    public boolean lock(String lock, long timeMs) {
        return (boolean)
                redisTemplate.execute(
                        (RedisCallback)
                                connection -> {
                                    // 获取时间毫秒值
                                    long expireAt = System.currentTimeMillis() + timeMs + 1;
                                    // 获取锁
                                    Boolean acquire =
                                            connection.setNX(
                                                    lock.getBytes(),
                                                    String.valueOf(expireAt).getBytes());
                                    if (acquire) {
                                        return true;
                                    } else {
                                        byte[] bytes = connection.get(lock.getBytes());
                                        // 非空判断
                                        if (Objects.nonNull(bytes) && bytes.length > 0) {
                                            long expireTime = Long.parseLong(new String(bytes));
                                            // 如果锁已经过期
                                            if (expireTime < System.currentTimeMillis()) {
                                                // 重新加锁，防止死锁
                                                byte[] set =
                                                        connection.getSet(
                                                                lock.getBytes(),
                                                                String.valueOf(
                                                                                System
                                                                                        .currentTimeMillis()
                                                                                        + timeMs
                                                                                        + 1)
                                                                        .getBytes());
                                                return Long.parseLong(new String(set))
                                                        < System.currentTimeMillis();
                                            }
                                        }
                                    }
                                    return false;
                                });
    }

}
