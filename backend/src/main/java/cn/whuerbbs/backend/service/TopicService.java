package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Topic;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    List<Topic> getTopicsByBoard(Board board);

    List<Topic> getTopicsByPostId(long postId);

    Optional<Topic> getTopicsById(int topicId);

    Pair<Long, Long> getStatistics(int topicId);
}
