package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Topic;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TopicService {
    List<Topic> getTopicsByBoard(Board board);

    List<Topic> getTopicsByPostId(long postId);

    Topic getTopicsById(long topicId);

    Pair<Long, Long> getStatistics(long topicId);

    boolean isValidTopic(Board anonymousPost, Set<Long> topicIds);
}
