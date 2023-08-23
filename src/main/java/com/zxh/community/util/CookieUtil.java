package com.zxh.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/23 17:54
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest req, String name) {
        if (req == null || name == null) {
            throw new IllegalArgumentException("参数为空！");
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
