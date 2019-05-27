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
import java.util.List;

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
    public void commentPost(String userId, CommentDTO commentDTO) {
        var now = LocalDateTime.now();
        var postOptional = postMapper.selectById(commentDTO.getPostId());
        var post = postOptional.orElseThrow(() -> new BusinessException("帖子不存在"));
        // 评论数
        postMapper.updateCommentCount(commentDTO.getPostId(), 1);
        // 帖子活跃时间
        postMapper.updateLastActiveTime(commentDTO.getPostId(), now);

        var comment = new Comment();
        comment.setPostId(commentDTO.getPostId());
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setUserId(userId);
        comment.setCreatedAt(now);
        commentMapper.insert(comment);

        NotificationType notificationType;
        String toUserId;
        String referenceId;
        // 一级评论
        if (commentDTO.getParentId() == 0) {
            notificationType = NotificationType.POST_COMMENTED;
            referenceId = String.valueOf(post.getId());
            toUserId = post.getUserId();
        }
        // 二级评论评论，postId以子评论为主，忽略传入参数的postId
        else {
            var parentCommentOptional = commentMapper.selectById(commentDTO.getParentId());
            var parentComment = parentCommentOptional.orElseThrow(() -> new BusinessException("父评论不存在"));
            commentDTO.setPostId(parentComment.getPostId());
            notificationType = NotificationType.COMMENT_REPLIED;
            // 帖子被回复时，返回一级评论id
            referenceId = String.valueOf(commentDTO.getParentId());
            toUserId = parentComment.getUserId();
        }

        // 通知的发起者永远是唤起接口的人
        // 当帖子所有者不是自己的时候，才发送通知
        if (!userId.equals(toUserId)) {
            var notification = new Notification();
            notification.setType(notificationType);
            notification.setContent(comment.getContent());
            notification.setReferenceId(referenceId);
            notification.setFromUserId(userId);
            notification.setToUserId(toUserId);
            notification.setBeRead(false);
            notification.setCreatedAt(now);
            notificationMapper.insert(notification);
        }
    }

    @Override
    public List<Comment> getHotComments(long postId) {
        // 目前五条热评
        return commentMapper.selectHotComments(postId, 5);
    }

    @Override
    public Comment getCommentById(long commentId) {
        return commentMapper.selectById(commentId).orElseThrow(() -> new BusinessException("评论不存在"));
    }

    @Override
    public List<Comment> getCommentsByParentId(long parentCommentId) {
        return commentMapper.selectByParentId(parentCommentId);
    }

    @Override
    public void deleteCommentById(long commentId) {
        // 删除子评论，但不删除通知
        // 减少帖子活跃度
        var commentOptional = commentMapper.selectById(commentId);
        commentOptional.ifPresent(comment ->
        {
            var count1 = commentMapper.deleteById(commentId);
            var count2 = commentMapper.deleteByParentId(commentId);
            postMapper.updateCommentCount(comment.getPostId(), -(count1 + count2));
        });
    }

    @Override
    public Page<Comment> getSubCommentsPageable(long commentId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var comments = commentMapper.selectByParentId(commentId);
        var pageInfo = new PageInfo<>(comments);
        return new PageImpl<>(comments, pageable, pageInfo.getTotal());
    }

    @Override
    public List<Comment> getAllSubComments(long parentCommentId) {
        return commentMapper.selectByParentId(parentCommentId);
    }
}
