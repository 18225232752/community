package com.zxh.community.config;

import com.zxh.community.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/28 11:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisConfigTest {

    @Autowired
    private RedisTemplate redisTemplate;

    private String key;

    @Test
    public void testStrings() {
        key = "test:count";
        redisTemplate.opsForValue().set(key, 1);

        System.out.println(redisTemplate.opsForValue().get(key));

        System.out.println(redisTemplate.opsForValue().increment(key));
        System.out.println(redisTemplate.opsForValue().decrement(key));
    }

    @Test
    public void testHashes() {
        key = "test:user";

        redisTemplate.opsForHash().put(key, "id", 1);
        redisTemplate.opsForHash().put(key, "username", "张三");

        System.out.println(redisTemplate.opsForHash().get(key, "id"));
        System.out.println(redisTemplate.opsForHash().get(key, "username"));

    }

    @Test
    public void testLists() {
        key = "test:ids";

        redisTemplate.opsForList().rightPushAll(key,"111", "222", "333");

        System.out.println(redisTemplate.opsForList().size(key));
        System.out.println(redisTemplate.opsForList().index(key, 1));
        System.out.println(redisTemplate.opsForList().range(key, 1, 2));
    }
    
    // 多次访问同一个key
    @Test
    public void testBoundOperations() {
        key = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(key);
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    // redis编程式事务

    @Test
    public void testTransactional() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                key = "test:tx";

                operations.multi();

                operations.opsForSet().add(key, "张三", "李四", "王五");

                System.out.println(operations.opsForSet().members(key));

                return operations.exec();
            }
        });
        System.out.println(obj);
    }
}