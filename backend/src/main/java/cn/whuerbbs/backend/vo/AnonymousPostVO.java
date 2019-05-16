package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;
import cn.whuerbbs.backend.model.User;

import java.util.List;

public class AnonymousPostVO extends PostVO {

    public AnonymousPostVO(Post post, List<String> images, List<Topic> topics, String anonymousName, String avatarurl) {
        super(post, images, topics);
        User user = new User();
        user.setNickname(anonymousName);
        user.setAvatarUrl(avatarurl);
        this.setCreatedBy(new UserVO(user));
    }
}
