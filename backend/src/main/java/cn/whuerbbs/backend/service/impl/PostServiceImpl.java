package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.exception.NotExistsException;
import cn.whuerbbs.backend.mapper.AnonymousPostMapper;
import cn.whuerbbs.backend.mapper.CommentMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.mapper.PostTopicMapper;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.User;
import cn.whuerbbs.backend.service.AttachmentService;
import cn.whuerbbs.backend.service.PostAttachmentService;
import cn.whuerbbs.backend.service.PostService;
import cn.whuerbbs.backend.service.TopicService;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.PostListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PostServiceImpl implements PostService {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostTopicMapper postTopicMapper;

    @Autowired
    private PostAttachmentService postAttachmentService;

    @Autowired
    private AnonymousPostMapper anonymousPostMapper;

    @Autowired
    private ImageUtil imageUtil;

    @Value("${static-images.default-anonymous-avatar}")
    private String defaultAnonymousAvatarPath;


    @Override
    public void create(String userId, PostDTO postDTO) {
        var post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setBoard(postDTO.getBoard());
        post.setContent(postDTO.getContent());
        post.setUserId(userId);
        post.setCreatedAt(LocalDateTime.now());
        post.setLastActiveAt(post.getCreatedAt());
        postMapper.insert(post);

        postAttachmentService.addAll(post.getId(), postDTO.getImages());

        if (Objects.nonNull(postDTO.getTopicId())) {
            var topicIds = Set.of(postDTO.getTopicId());
            if (topicService.isValidTopic(post.getBoard(), topicIds)) {
                postTopicMapper.insertBatch(post.getId(), topicIds);
            } else {
                throw new BusinessException("非法主题");
            }
        }
    }

    @Override
    public Page<Post> getPageableByBoard(Board board, Pageable pageable) {
        var post = new Post();
        post.setBoard(board);
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        var posts = postMapper.selectPageable(post);
        var pageInfo = new PageInfo<>(posts);
        return new PageImpl<>(posts, pageable, pageInfo.getTotal());
    }

    @Override
    public Post getById(long postId) {
        return postMapper.selectById(postId).orElseThrow(() -> new NotExistsException("帖子不存在"));
    }

    @Override
    public Page<Post> getPostsPageableByTopicId(long topicId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        var posts = postMapper.selectPostsByTopicId(topicId);
        var pageInfo = new PageInfo<>(posts);
        return new PageImpl<>(posts, pageable, pageInfo.getTotal());
    }

    @Override
    public Page<Post> getHotPostsPageableByTopicId(long topicId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        var posts = postMapper.selectHotPostIdsByTopicId(topicId);
        var pageInfo = new PageInfo<>(posts);
        return new PageImpl<>(posts, pageable, pageInfo.getTotal());
    }

    @Override
    public PostListVO getPostListVO(Post post) {
        var attachmentOptional = attachmentService.getFirstByPostId(post.getId());
        var topics = topicService.getTopicsByPostId(post.getId());
        if (post.getBoard() == Board.ANONYMOUS_POST) {
            post = addAnonymousInfo(post);
        }
        return new PostListVO(post, attachmentOptional.map(attachment -> imageUtil.getFullPath(attachment.getPath())).orElse(null), topics);
    }

    @Override
    public List<PostListVO> getPostListVO(List<Long> postIds) {
        if (postIds.isEmpty()) {
            return List.of();
        }
        return postMapper.selectByIds(postIds).stream().map(this::getPostListVO).collect(Collectors.toList());
    }

    @Override
    public Post addAnonymousInfo(Post post) {
        var user = new User();
        var anonymousPostOptional = anonymousPostMapper.selectByPostId(post.getId());
        var anonymousPost = anonymousPostOptional.orElseThrow(() -> new NotExistsException("帖子不存在"));
        user.setAvatarUrl(imageUtil.getFullPath(defaultAnonymousAvatarPath));
        user.setNickname(anonymousPost.getAnonymousName());
        post.setUser(user);
        return post;
    }

    @Override
    public void deleteById(long postId) {
        // 删除帖子
        postMapper.deleteById(postId);
        // 删除所有子评论
        commentMapper.deleteByPostId(postId);
    }
}
