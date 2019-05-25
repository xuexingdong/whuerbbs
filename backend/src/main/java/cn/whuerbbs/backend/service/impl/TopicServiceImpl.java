package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.mapper.PostTopicMapper;
import cn.whuerbbs.backend.mapper.TopicMapper;
import cn.whuerbbs.backend.model.Topic;
import cn.whuerbbs.backend.service.TopicService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private PostTopicMapper postTopicMapper;

    @Override
    public List<Topic> getTopicsByBoard(Board board) {
        return topicMapper.selectByBoard(board);
    }

    @Override
    public List<Topic> getTopicsByPostId(long postId) {
        var topicIds = postTopicMapper.selectTopicIds(postId);
        if (topicIds.isEmpty()) {
            return List.of();
        } else {
            return topicMapper.selectByIds(topicIds);
        }
    }

    @Override
    public Optional<Topic> getTopicsById(int topicId) {
        return topicMapper.selectById(topicId);
    }

    @Override
    public Pair<Long, Long> getStatistics(int topicId) {
        var resultMap = topicMapper.getParticipateUserCountAndDiscussionMountById(topicId);
        return Pair.of(Long.parseLong(String.valueOf(resultMap.get("count1"))), Long.parseLong(String.valueOf(resultMap.get("count2"))));
    }

    @Override
    public boolean isValidTopic(Board board, Set<Long> topicIds) {
        return topicMapper.selectByBoard(board).stream().map(Topic::getId).collect(Collectors.toSet()).containsAll(topicIds);
    }
}
