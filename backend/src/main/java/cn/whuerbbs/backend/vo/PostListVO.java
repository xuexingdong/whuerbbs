package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.common.Constants;
import cn.whuerbbs.backend.model.Post;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class PostListVO {
    private long id;
    private UserVO createdBy;
    private String title;
    private String summary;
    private String image;
    private long likeCount;
    private long commentCount;
    private LocalDateTime lastActiveAt;

    public PostListVO(Post post, String image) {
        BeanUtils.copyProperties(post, this);
        this.image = image;
        this.createdBy = new UserVO(post.getUser());
        this.summary = post.getContent().substring(0, Math.min(Constants.POST_SUMMARY_LENGTH, post.getContent().length()));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserVO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserVO createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
}
