package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface TopicMapper {
    List<Topic> selectByBoard(Board board);

    List<Topic> selectByIds(List<Long> topicIds);

    Optional<Topic> selectByTitle(String title);

    List<Topic> selectByTitles(@Param("board") Board board, @Param("titles") Set<String> titles);

    Optional<Topic> selectById(int id);

    Map<String, Object> getParticipateUserCountAndDiscussionMountById(int id);
}
