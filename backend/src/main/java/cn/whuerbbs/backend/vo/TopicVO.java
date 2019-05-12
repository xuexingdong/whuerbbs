package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.Topic;

public class TopicVO {
    private long id;
    private String title;

    public TopicVO(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
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
}
