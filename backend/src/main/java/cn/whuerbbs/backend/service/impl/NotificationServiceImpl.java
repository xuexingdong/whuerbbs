package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.common.Constants;
import cn.whuerbbs.backend.mapper.CommentMapper;
import cn.whuerbbs.backend.mapper.NotificationMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.model.Notification;
import cn.whuerbbs.backend.service.NotificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Page<Notification> getPageable(String userId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var notifications = notificationMapper.selectByToUserIdPageable(userId);
        // 设置所有消息为已读
        notificationMapper.setAllRead(userId);
        var pageInfo = new PageInfo<>(notifications);
        return new PageImpl<>(notifications, pageable, pageInfo.getTotal());
    }

    @Override
    public long countUnreadByUserId(String userId) {
        return notificationMapper.countUnreadByToUserId(userId);
    }

    @Override
    public String getSummary(Notification notification) {
        String summary = null;
        switch (notification.getType()) {
            case POST_LIKED:
                var postOptional = postMapper.selectById(Long.parseLong(notification.getReferenceId()));
                if (postOptional.isPresent()) {
                    summary = postOptional.get().getTitle().substring(0, Math.min(Constants.NOTIFICATION_SUMMARY_LENGTH, postOptional.get().getTitle().length()));
                }
                break;
            case POST_COMMENTED:
            case COMMENT_LIKED:
            case COMMENT_REPLIED:
                var commentOptional = commentMapper.selectById(Long.parseLong(notification.getReferenceId()));
                if (commentOptional.isPresent()) {
                    summary = commentOptional.get().getContent().substring(0, Math.min(Constants.NOTIFICATION_SUMMARY_LENGTH, commentOptional.get().getContent().length()));
                }
                break;
            default:
                summary = "";
        }
        return summary;
    }
}
