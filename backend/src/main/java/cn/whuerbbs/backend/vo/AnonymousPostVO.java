package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;
import cn.whuerbbs.backend.model.User;

import java.util.List;

public class AnonymousPostVO extends PostVO {

    public AnonymousPostVO(Post post, List<String> images, List<Topic> topics, boolean collected, CurrentUserData currentUserData, String anonymousName, String avatarUrl) {
        super(post, images, topics, collected, currentUserData);
        User user = new User();
        user.setNickname(anonymousName);
        user.setAvatarUrl(avatarUrl);
        this.setCreatedBy(new UserVO(user));
    }
}
