package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;
import cn.whuerbbs.backend.model.User;

import java.util.List;

public class AnonymousPostListVO extends PostListVO {
    public AnonymousPostListVO(Post post, String image, List<Topic> topics, String anonymousName) {
        super(post, image, topics);
        User user = new User();
        user.setNickname(anonymousName);
        // TODO 默认头像地址
        user.setAvatarUrl("");
        this.setCreatedBy(new UserVO(user));
    }
}
