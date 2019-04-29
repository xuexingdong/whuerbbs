package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Gender;
import cn.whuerbbs.backend.model.User;
import org.springframework.beans.BeanUtils;

public class UserVO {
    private String id;
    private String nickname;
    private String avatarUrl;
    private Gender gender;

    public UserVO(User user) {
        BeanUtils.copyProperties(user, this);
        this.id = user.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
