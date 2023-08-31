package com.zxh.community.service;

import com.zxh.community.entity.DiscussPost;
import org.springframework.data.domain.Page;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/31 22:20
 */
public interface ElasticsearchService {
    void saveDiscussPost(DiscussPost post);

    void deleteDiscussPost(int id);

    Page<DiscussPost> searchDiscussPost(String keyword, int current, int limit);
}
