package com.zxh.community.mapper;

import com.zxh.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/23 14:38
 */
@Mapper
public interface LoginTicketMapper {
    int insertLoginTicket(LoginTicket loginTicket);

    LoginTicket selectByTicket(String ticket);

    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
