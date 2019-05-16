package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.dto.CommentDTO;
import cn.whuerbbs.backend.enumeration.NotificationType;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.mapper.CommentMapper;
import cn.whuerbbs.backend.mapper.NotificationMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.model.Comment;
import cn.whuerbbs.backend.model.Notification;
import cn.whuerbbs.backend.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public Page<Comment> getPageable(long postId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var comments = commentMapper.selectByPostIdPageable(postId);
        var pageInfo = new PageInfo<>(comments);
        return new PageImpl<>(comments, pageable, pageInfo.getTotal());
    }

    @Override
    public Comment commentPost(String userId, CommentDTO commentDTO) {
        var now = LocalDateTime.now();
        var comment = new Comment();
        comment.setPostId(commentDTO.getPostId());
        comment.setContent(commentDTO.getContent());
        comment.setUserId(userId);
        comment.setCreatedAt(now);
        commentMapper.insert(comment);

        var postOptional = postMapper.selectById(comment.getPostId());
        var post = postOptional.orElseThrow(() -> new BusinessException("帖子不存在"));
        postMapper.updateCommentCount(comment.getPostId(), 1);
        // 帖子活跃时间
        postMapper.updateLastActiveTime(comment.getPostId(), now);

        // 当帖子所有者不是自己的时候，才发送通知
        if (!post.getUserId().equals(userId)) {
            var notification = new Notification();
            notification.setType(NotificationType.POST_COMMENTED);
            notification.setContent(comment.getContent());
            notification.setReferenceId(String.valueOf(comment.getId()));
            notification.setFromUserId(userId);
            notification.setToUserId(post.getUserId());
            notification.setBeRead(false);
            notification.setCreatedAt(now);
            notificationMapper.insert(notification);
        }
        return comment;
    }
}
