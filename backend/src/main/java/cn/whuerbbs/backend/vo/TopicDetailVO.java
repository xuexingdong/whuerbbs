package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Topic;
import org.springframework.beans.BeanUtils;

public class TopicDetailVO extends TopicVO {
    private Board board;
    private String description;
    private String image;
    private long participateUserCount;
    private long discussionAmount;


    public TopicDetailVO(Topic topic, String image, long participateUserCount, long discussionAmount) {
        super(topic);
        BeanUtils.copyProperties(topic, this);
        this.image = image;
        this.participateUserCount = participateUserCount;
        this.discussionAmount = discussionAmount;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getParticipateUserCount() {
        return participateUserCount;
    }

    public void setParticipateUserCount(long participateUserCount) {
        this.participateUserCount = participateUserCount;
    }

    public long getDiscussionAmount() {
        return discussionAmount;
    }

    public void setDiscussionAmount(long discussionAmount) {
        this.discussionAmount = discussionAmount;
    }
}
