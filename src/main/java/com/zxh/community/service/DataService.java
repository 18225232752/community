package com.zxh.community.service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 9:47
 */
public interface DataService {

    void recordUV(String ip);

    long calculateUV(Date start, Date end);

    void recordDAU(int userId);

    long calculateDAU(Date start, Date end);

}
