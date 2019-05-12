package cn.whuerbbs.backend.dto;

import javax.validation.constraints.NotNull;

public class AnonymousPostDTO extends PostDTO {
    private Long topicId;
    @NotNull
    private String anonymousName;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getAnonymousName() {
        return anonymousName;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }
}
