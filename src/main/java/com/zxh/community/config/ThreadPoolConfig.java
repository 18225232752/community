package com.zxh.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 11:54
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
