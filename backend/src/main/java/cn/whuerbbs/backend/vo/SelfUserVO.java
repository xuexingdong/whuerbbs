package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.User;

public class SelfUserVO extends UserVO {
    private long notificationCount;

    public SelfUserVO(User user, long notificationCount) {
        super(user);
        this.notificationCount = notificationCount;
    }

    public long getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(long notificationCount) {
        this.notificationCount = notificationCount;
    }
}


