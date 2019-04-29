package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.NotificationType;
import cn.whuerbbs.backend.model.Notification;
import org.springframework.beans.BeanUtils;

public class NotificationVO {
    private long id;
    private NotificationType type;
    private String referenceId;
    private String content;
    private String summary;
    private UserVO fromUser;

    public NotificationVO(Notification notification) {
        BeanUtils.copyProperties(notification, this);
        this.id = notification.getId();
        this.fromUser = new UserVO(notification.getFromUser());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public UserVO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserVO fromUser) {
        this.fromUser = fromUser;
    }
}
