package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Gender;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;

import java.util.List;

public class AnonymousPostVO extends PostVO {

    public AnonymousPostVO(Post post, List<String> images, List<Topic> topics, String anonymousName) {
        super(post, images, topics);
        this.getCreatedBy().setId(null);
        this.getCreatedBy().setGender(Gender.UNKNOWN);
        // TODO 默认头像地址
        this.getCreatedBy().setAvatarUrl("");
        this.getCreatedBy().setNickname(anonymousName);
    }
}
