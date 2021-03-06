package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.enumeration.Board;
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
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
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
    public Board getBoard(Notification notification) {
        Board board = null;
        switch (notification.getType()) {
            case POST_COMMENTED:
            case POST_LIKED:
                final var postOptional = postMapper.selectById(Long.parseLong(notification.getReferenceId()));
                if (postOptional.isPresent()) {
                    board = postOptional.get().getBoard();
                }
                break;
            case COMMENT_LIKED:
            case COMMENT_REPLIED:
            case SUB_COMMENT_LIKED:
                var commentOptional = commentMapper.selectById(Long.parseLong(notification.getReferenceId()));
                if (commentOptional.isPresent()) {
                    var comment = commentOptional.get();
                    var postOptional2 = postMapper.selectById(comment.getPostId());
                    if (postOptional2.isPresent()) {
                        board = postOptional2.get().getBoard();
                    }
                }
                break;
            default:
                break;
        }
        return board;
    }
}
