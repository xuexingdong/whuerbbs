package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.User;

public class SelfUserDetailVO extends UserDetailVO {
    private long notificationCount;

    public SelfUserDetailVO(User user, long notificationCount) {
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


