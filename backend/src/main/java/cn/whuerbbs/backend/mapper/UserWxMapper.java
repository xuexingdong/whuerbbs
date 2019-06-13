package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.UserWx;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserWxMapper {
    int insert(UserWx userWx);

    Optional<UserWx> selectByOpenid(String openid);
}
