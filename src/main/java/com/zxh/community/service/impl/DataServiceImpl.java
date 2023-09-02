package com.zxh.community.service.impl;

import com.zxh.community.service.DataService;
import com.zxh.community.util.RedisKeyUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 9:52
 */
@Service
public class DataServiceImpl implements DataService {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    // 将指定的IP计入UV
    @Override
    public void recordUV(String ip) {
        String redisUVKey = RedisKeyUtil.getUVKey(sdf.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(redisUVKey, ip);
    }

    // 统计指定日期范围内的UV
    @Override
    public long calculateUV(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 整理该日期范围内的key
        List<String > keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        if (!calendar.getTime().after(end)) {
            String redisUVKey = RedisKeyUtil.getUVKey(sdf.format(calendar.getTime()));
            keyList.add(redisUVKey);
            calendar.add(Calendar.DATE, 1);
        }

        // 合并数据
        String redisUVKey = RedisKeyUtil.getUVKey(sdf.format(start), sdf.format(end));
        redisTemplate.opsForHyperLogLog().union(redisUVKey, keyList.toArray());

        // 返回统计的结果
        return redisTemplate.opsForHyperLogLog().size(redisUVKey);
    }

    // 将指定用户计入DAU
    @Override
    public void recordDAU(int userId) {
        String redisDAUKey = RedisKeyUtil.getDAUKey(sdf.format(new Date()));
        redisTemplate.opsForValue().setBit(redisDAUKey, userId, true);
    }

    // 统计指定日期范围内的DAU
    @Override
    public long calculateDAU(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 整理该日期范围内的key
        List<byte[]> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String redisDAUKey = RedisKeyUtil.getDAUKey(sdf.format(calendar.getTime()));
            keyList.add(redisDAUKey.getBytes());
            calendar.add(Calendar.DATE, 1);
        }

        // 进行or运行
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                String redisDAUKey = RedisKeyUtil.getDAUKey(sdf.format(start), sdf.format(end));
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisDAUKey.getBytes(), keyList.toArray(new byte[0][0]));
                return connection.bitCount(redisDAUKey.getBytes());
            }
        });
    }
}
