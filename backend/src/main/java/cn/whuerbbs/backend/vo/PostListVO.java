package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.common.Constants;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostListVO {
    private long id;
    private UserVO createdBy;
    private String title;
    private String summary;
    private Board board;
    private String image;
    private List<TopicVO> topics;
    private long likeCount;
    private long commentCount;
    private LocalDateTime lastActiveAt;

    public PostListVO(Post post, String image, List<Topic> topics) {
        BeanUtils.copyProperties(post, this);
        this.image = image;
        this.createdBy = new UserVO(post.getUser());
        this.summary = post.getContent().substring(0, Math.min(Constants.POST_SUMMARY_LENGTH, post.getContent().length()));
        this.topics = topics.stream().map(TopicVO::new).collect(Collectors.toList());
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<TopicVO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicVO> topics) {
        this.topics = topics;
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
