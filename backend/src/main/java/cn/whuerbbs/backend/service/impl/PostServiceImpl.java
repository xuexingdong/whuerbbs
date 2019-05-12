package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.mapper.PostAttachmentMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.PostAttachment;
import cn.whuerbbs.backend.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostAttachmentMapper postAttachmentMapper;

    @Override
    public void create(String userId, PostDTO postDTO) {
        var post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUserId(userId);
        post.setCreatedAt(LocalDateTime.now());
        post.setLastActiveAt(post.getCreatedAt());
        postMapper.insert(post);

        var postAttachments = postDTO.getImages().stream().map(attachmentId -> {
            var postAttachment = new PostAttachment();
            postAttachment.setPostId(post.getId());
            postAttachment.setAttachmentId(attachmentId);
            return postAttachment;
        }).collect(Collectors.toList());
        if (!postAttachments.isEmpty()) {
            postAttachmentMapper.insertBatch(postAttachments);
        }
    }

    @Override
    public Page<Post> getPageableByBoard(Pageable pageable, Board board) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var post = new Post();
        post.setBoard(board);
        var posts = postMapper.selectPageable(post);
        var pageInfo = new PageInfo<>(posts);
        return new PageImpl<>(posts, pageable, pageInfo.getTotal());
    }

    @Override
    public Optional<Post> getById(long postId) {
        return postMapper.selectById(postId);
    }

    @Override
    public Page<Post> getPostsPageableByTopicId(Pageable pageable, int topicId) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var posts = postMapper.selectPostIdsByTopicId(topicId);
        var pageInfo = new PageInfo<>(posts);
        return new PageImpl<>(posts, pageable, pageInfo.getTotal());
    }

    @Override
    public Page<Post> getHotPostsPageableByTopicId(Pageable pageable, int topicId) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var posts = postMapper.selectHotPostIdsByTopicId(topicId);
        var pageInfo = new PageInfo<>(posts);
        return new PageImpl<>(posts, pageable, pageInfo.getTotal());
    }
}
