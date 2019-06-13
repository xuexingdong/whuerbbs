package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.model.base.BaseModel;
import cn.whuerbbs.backend.model.base.Identifiable;

public class Attitude extends BaseModel implements Identifiable<Long> {
    private Long id;
    private String userId;
    private AttitudeTarget target;
    private String targetId;
    private AttitudeStatus status;

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

    public AttitudeTarget getTarget() {
        return target;
    }

    public void setTarget(AttitudeTarget target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public AttitudeStatus getStatus() {
        return status;
    }

    public void setStatus(AttitudeStatus status) {
        this.status = status;
    }
}
