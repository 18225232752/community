package com.zxh.community.mapper;

import com.zxh.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 17:50
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit, @Param("orderMode") int orderMode);

    int selectDiscussPostRows(int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    int updateType(@Param("id") int id, @Param("type") int type);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    int updateScore(@Param("id") int id, @Param("score") double score);

}
