package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;
import cn.whuerbbs.backend.model.User;

import java.util.List;

public class AnonymousPostListVO extends PostListVO {
    public AnonymousPostListVO(Post post, String image, List<Topic> topics, String anonymousName, String avatarUrl) {
        super(post, image, topics);
        User user = new User();
        user.setNickname(anonymousName);
        user.setAvatarUrl(avatarUrl);
        this.setCreatedBy(new UserVO(user));
    }
}
