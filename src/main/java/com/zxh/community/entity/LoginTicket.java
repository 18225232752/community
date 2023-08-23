package com.zxh.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/23 14:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket {
    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    private Date expired;
}
