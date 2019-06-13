package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.model.base.BaseModel;
import cn.whuerbbs.backend.model.base.Identifiable;

public class UserWx extends BaseModel implements Identifiable<Long> {
    private Long id;
    private String userId;
    private String openid;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
