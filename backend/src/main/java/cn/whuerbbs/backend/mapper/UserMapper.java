package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int insert(User user);

    Optional<User> selectById(String id);
}
