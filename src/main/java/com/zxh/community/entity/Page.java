package com.zxh.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 20:15
 *
 * 分装分页相关信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    // 当前页面
    private int current = 1;

    // 显示上限
    private int limit = 10;

    // 记录总数
    private int rows;

    // 查询路径(用于复用分页链接)
    private String path;

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    // 获取当前页起始行
    public int getOffset() {
        return (current - 1) * limit;
    }

    // 获取总页数
    public int getTotal() {
        return rows % limit == 0 ? rows / limit : rows / limit + 1;
    }

    // 获取起始页码
    public int getFrom() {
        int from = current - 2;
        return Math.max(from, 1);
    }

    // 获取结束页码
    public int getTo() {
        int to = current + 2;
        int total = this.getTotal();
        return Math.min(to, total);
    }
}
