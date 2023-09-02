package com.zxh.community.config;

import com.zxh.community.controller.interceptor.DataInterceptor;
import com.zxh.community.controller.interceptor.LoginRequiredInterceptor;
import com.zxh.community.controller.interceptor.LoginTicketInterceptor;
import com.zxh.community.controller.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/23 17:39
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource(name = "loginTicketInterceptor")
    private LoginTicketInterceptor loginTicketInterceptor;

    // @Resource(name = "loginRequiredInterceptor")
    // private LoginRequiredInterceptor loginRequiredInterceptor;

    @Resource(name = "messageInterceptor")
    private MessageInterceptor messageInterceptor;

    @Resource(name = "dataInterceptor")
    private DataInterceptor dataInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        // registry.addInterceptor(loginRequiredInterceptor)
        //         .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        registry.addInterceptor(dataInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
    }
}
