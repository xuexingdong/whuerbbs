package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.model.Comment;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class CommentVO {
    private long id;
    private String content;
    private long likeCount;
    private LocalDateTime createdAt;
    private UserVO createdBy;
    private AttitudeStatus attitudeStatus;
    private boolean canDelete;

    public CommentVO(Comment comment, CurrentUserData currentUserData) {
        BeanUtils.copyProperties(comment, this);
        this.id = comment.getId();
        this.createdBy = new UserVO(comment.getUser());
        this.canDelete = currentUserData.getUserId().equals(comment.getUserId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserVO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserVO createdBy) {
        this.createdBy = createdBy;
    }

    public AttitudeStatus getAttitudeStatus() {
        return attitudeStatus;
    }

    public void setAttitudeStatus(AttitudeStatus attitudeStatus) {
        this.attitudeStatus = attitudeStatus;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
