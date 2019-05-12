package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.dto.AnonymousPostDTO;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.mapper.AnonymousPostMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.mapper.PostTopicMapper;
import cn.whuerbbs.backend.model.AnonymousPost;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.service.AnonymousPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class AnonymousPostServiceImpl implements AnonymousPostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostTopicMapper postTopicMapper;

    @Autowired
    private AnonymousPostMapper anonymousPostMapper;

    @Override
    public void publish(String userId, AnonymousPostDTO anonymousPostDTO) {
        var now = LocalDateTime.now();
        var post = new Post();
        BeanUtils.copyProperties(anonymousPostDTO, post);
        post.setBoard(Board.ANONYMOUS_POST);
        post.setUserId(userId);
        post.setCreatedAt(now);
        post.setLastActiveAt(now);
        postMapper.insert(post);

        var anonymousPost = new AnonymousPost();
        anonymousPost.setPostId(post.getId());
        anonymousPost.setAnonymousName(anonymousPostDTO.getAnonymousName());
        anonymousPostMapper.insert(anonymousPost);

        if (Objects.nonNull(anonymousPostDTO.getTopicId())) {
            postTopicMapper.insertBatch(post.getId(), Set.of(anonymousPostDTO.getTopicId()));
        }
    }

    @Override
    public Optional<AnonymousPost> getByPostId(long postId) {
        return anonymousPostMapper.selectByPostId(postId);
    }
}
