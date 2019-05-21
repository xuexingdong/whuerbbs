package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    List<Notification> selectByToUserIdPageable(String userId);

    int setAllRead(String userId);

    int insert(Notification notification);

    long countUnreadByToUserId(String userId);
}
