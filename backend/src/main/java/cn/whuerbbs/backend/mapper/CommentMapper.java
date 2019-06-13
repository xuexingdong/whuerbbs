package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    // 只有一级评论
    List<Comment> selectByPostIdPageable(long postId);

    int insert(Comment comment);

    long countByPostId(long postId);

    Optional<Comment> selectById(long id);

    int updateLikeCount(@Param("id") long id, @Param("likeCount") int likeCount);

    List<Comment> selectHotComments(@Param("postId") long postId, @Param("n") int n);

    List<Comment> selectByParentId(long parentCommentId);

    int deleteById(long id);

    int deleteByParentId(long parentCommentId);

    int deleteByPostId(long postId);
}
