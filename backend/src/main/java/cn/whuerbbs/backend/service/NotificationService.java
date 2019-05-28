package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Page<Notification> getPageable(String userId, Pageable pageable);

    long countUnreadByUserId(String userId);

    Board getBoard(Notification notification);
}
