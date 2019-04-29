package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.model.Post;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

public class PostVO {
    private long id;
    private String title;
    private String content;
    private List<String> images;
    private long likeCount;
    private long commentCount;
    private UserVO createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;
    private AttitudeStatus attitudeStatus;

    public PostVO(Post post, List<String> images) {
        BeanUtils.copyProperties(post, this);
        this.createdBy = new UserVO(post.getUser());
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public UserVO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserVO createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public AttitudeStatus getAttitudeStatus() {
        return attitudeStatus;
    }

    public void setAttitudeStatus(AttitudeStatus attitudeStatus) {
        this.attitudeStatus = attitudeStatus;
    }
}
