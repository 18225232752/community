package com.zxh.community.config;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 21:58
 */
@Configuration
public class WkConfig {

    private static final Logger logger = LoggerFactory.getLogger(WkConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init() {
        // 创建WK图片目录
        File file = new File(wkImageStorage);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (mkdir) {
                logger.info("创建WK图片目录成功！：" + wkImageStorage);
            } else {
                logger.error("创建WK图片目录失败！：" + wkImageStorage);
            }
        }
    }
}
