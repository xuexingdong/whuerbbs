package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.dto.SecondhandPostDTO;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.mapper.PostTopicMapper;
import cn.whuerbbs.backend.mapper.SecondhandPostMapper;
import cn.whuerbbs.backend.mapper.TopicMapper;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.SecondhandPost;
import cn.whuerbbs.backend.model.Topic;
import cn.whuerbbs.backend.service.SecondhandPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class SecondhandPostServiceImpl implements SecondhandPostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private SecondhandPostMapper secondhandPostMapper;

    @Autowired
    private PostTopicMapper postTopicMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public void publish(String userId, SecondhandPostDTO secondHandPostDTO) {
        var now = LocalDateTime.now();
        var post = new Post();
        BeanUtils.copyProperties(secondHandPostDTO, post);
        post.setBoard(Board.SECONDHAND);
        post.setUserId(userId);
        post.setCreatedAt(now);
        post.setLastActiveAt(now);
        postMapper.insert(post);

        var secondhandPost = new SecondhandPost();
        secondhandPost.setPostId(post.getId());
        secondhandPost.setTradeCategory(secondHandPostDTO.getTradeCategory());
        secondhandPost.setCampus(secondHandPostDTO.getCampus());
        secondhandPostMapper.insert(secondhandPost);

        // 将物品分类与交易区域插入话题
        var topics = topicMapper.selectByTitles(Board.SECONDHAND, Set.of(secondHandPostDTO.getTradeCategory().getTitle(), secondHandPostDTO.getCampus().getTitle()));
        if (topics.size() != 2) {
            throw new BusinessException("话题错误");
        }
        postTopicMapper.insertBatch(post.getId(), topics.stream().map(Topic::getId).collect(Collectors.toSet()));


    }

    @Override
    public Optional<SecondhandPost> getByPostId(long postId) {
        return secondhandPostMapper.selectByPostId(postId);
    }
}
