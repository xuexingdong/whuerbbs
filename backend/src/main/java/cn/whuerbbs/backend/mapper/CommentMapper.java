package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    List<Comment> selectByPostIdPageable(long postId);

    int insert(Comment comment);

    long countByPostId(long postId);

    Optional<Comment> selectById(long id);

    int updateLikeCount(@Param("id") long id, @Param("likeCount") int likeCount);
}
