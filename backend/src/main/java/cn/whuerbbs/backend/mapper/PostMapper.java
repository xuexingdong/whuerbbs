package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    int insert(Post post);

    List<Post> selectPageable(Post post);

    Optional<Post> selectById(long id);

    int updateLastActiveTime(@Param("id") long id, @Param("lastActiveAt") LocalDateTime lastActiveAt);

    int updateLikeCount(@Param("id") long id, @Param("likeCount") long likeCount);

    int updateCommentCount(@Param("id") long id, @Param("commentCount") long commentCount);

    List<Post> selectByIds(List<Long> ids);

    List<Post> selectPostIdsByTopicId(int topicId);

    List<Post> selectHotPostIdsByTopicId(int topicId);

    int deleteById(long id);
}
