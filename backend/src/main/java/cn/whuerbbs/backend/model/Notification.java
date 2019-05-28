package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.enumeration.NotificationType;
import cn.whuerbbs.backend.model.base.BaseModel;
import cn.whuerbbs.backend.model.base.Identifiable;

public class Notification extends BaseModel implements Identifiable<Long> {
    private Long id;
    private NotificationType type;
    private String summary;
    private String content;
    // read是SQL关键字
    private boolean beRead;
    private String referenceId;
    private String fromUserId;
    private String toUserId;

    private User fromUser;

    private User toUser;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isBeRead() {
        return beRead;
    }

    public void setBeRead(boolean beRead) {
        this.beRead = beRead;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}