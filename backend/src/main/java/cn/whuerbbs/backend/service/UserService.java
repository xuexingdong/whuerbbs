package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.WxLoginDTO;
import cn.whuerbbs.backend.model.Token;
import cn.whuerbbs.backend.model.User;

import java.util.Optional;

public interface UserService {

    Token wxLogin(WxLoginDTO wxLoginDTO);

    Optional<User> getById(String userId);

    void logout(String token);
}
