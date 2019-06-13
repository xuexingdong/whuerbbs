package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;

public interface AttitudeService {
    void add(String userId, AttitudeTarget target, String targetId, AttitudeStatus status);

    void delete(String userId, AttitudeTarget target, String targetId);

    long count(AttitudeTarget target, String targetId, AttitudeStatus status);

    AttitudeStatus getAttitudeStatus(String userId, AttitudeTarget target, String targetId);
}
