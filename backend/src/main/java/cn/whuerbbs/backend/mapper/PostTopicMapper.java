package cn.whuerbbs.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface PostTopicMapper {
    List<Long> selectTopicIds(long postId);

    List<Long> selectPostIds(long topicId);

    int insertBatch(@Param("postId") Long postId, @Param("topicIds") Set<Long> topicIds);
}
