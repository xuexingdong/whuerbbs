package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.common.Constants;
import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.enumeration.NotificationType;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.mapper.AttitudeMapper;
import cn.whuerbbs.backend.mapper.CommentMapper;
import cn.whuerbbs.backend.mapper.NotificationMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.model.Attitude;
import cn.whuerbbs.backend.model.Notification;
import cn.whuerbbs.backend.service.AttitudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class AttitudeServiceImpl implements AttitudeService {

    @Autowired
    private AttitudeMapper attitudeMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public void add(String userId, AttitudeTarget target, String targetId, AttitudeStatus status) {
        var attitude = new Attitude();
        attitude.setTarget(target);
        attitude.setTargetId(targetId);
        attitude.setStatus(status);
        attitude.setUserId(userId);
        attitude.setCreatedAt(LocalDateTime.now());
        var success = attitudeMapper.insert(attitude);
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return;
        }

        postMapper.updateLastActiveTime(Long.parseLong(attitude.getTargetId()), LocalDateTime.now());

        var notification = new Notification();
        String summary;
        switch (target) {
            case POST:
                postMapper.updateLikeCount(Long.parseLong(attitude.getTargetId()), 1);
                notification.setType(NotificationType.POST_LIKED);
                var postOptional = postMapper.selectById(Integer.parseInt(targetId));
                var post = postOptional.orElseThrow(() -> new BusinessException("帖子不存在"));
                summary = post.getTitle().substring(0, Math.min(Constants.NOTIFICATION_SUMMARY_LENGTH, post.getTitle().length()));
                notification.setReferenceId(String.valueOf(post.getId()));
                notification.setToUserId(post.getUserId());
                break;
            case COMMENT:
                commentMapper.updateLikeCount(Long.parseLong(attitude.getTargetId()), 1);
                var commentOptional = commentMapper.selectById(Integer.parseInt(targetId));
                var comment = commentOptional.orElseThrow(() -> new BusinessException("评论不存在"));
                // 一级评论被点赞，跳转到帖子详情页
                if (comment.getParentId() == 0) {
                    notification.setType(NotificationType.COMMENT_LIKED);
                    notification.setReferenceId(String.valueOf(comment.getPostId()));
                }
                // 二级评论被点赞，跳转到一级评论详情页
                else {
                    notification.setType(NotificationType.SUB_COMMENT_LIKED);
                    notification.setReferenceId(String.valueOf(comment.getParentId()));
                }
                summary = comment.getContent().substring(0, Math.min(Constants.NOTIFICATION_SUMMARY_LENGTH, comment.getContent().length()));
                notification.setToUserId(comment.getUserId());
                break;
            default:
                throw new BusinessException("不支持");
        }
        // 发送通知和接收通知的不是同一个人
        if (!userId.equals(notification.getToUserId())) {
            notification.setSummary(summary);
            notification.setContent("");
            notification.setFromUserId(userId);
            notification.setBeRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationMapper.insert(notification);
        }
    }

    @Override
    public void delete(String userId, AttitudeTarget target, String targetId) {
        var attitude = new Attitude();
        attitude.setTarget(target);
        attitude.setTargetId(targetId);
        attitude.setUserId(userId);
        int deletedRecordCount;
        try {
            deletedRecordCount = attitudeMapper.delete(attitude);
        } catch (DuplicateKeyException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return;
        }
        if (deletedRecordCount > 0) {
            switch (target) {
                case POST:
                    postMapper.updateLikeCount(Long.parseLong(attitude.getTargetId()), -1);
                    break;
                case COMMENT:
                    commentMapper.updateLikeCount(Long.parseLong(attitude.getTargetId()), -1);
                    break;
                default:
                    throw new BusinessException("不支持");
            }
        }
    }

    @Override
    public long count(AttitudeTarget target, String targetId, AttitudeStatus status) {
        var attitude = new Attitude();
        attitude.setTarget(target);
        attitude.setTargetId(targetId);
        attitude.setStatus(status);
        return attitudeMapper.count(attitude);
    }

    @Override
    public AttitudeStatus getAttitudeStatus(String userId, AttitudeTarget target, String targetId) {
        var example = new Attitude();
        example.setUserId(userId);
        example.setTarget(target);
        example.setTargetId(targetId);
        var attitude = attitudeMapper.select(example);
        if (attitude.isPresent()) {
            return attitude.get().getStatus();
        }
        return AttitudeStatus.DEFAULT;
    }
}
