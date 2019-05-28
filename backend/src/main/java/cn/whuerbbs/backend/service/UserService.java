package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.WxLoginDTO;
import cn.whuerbbs.backend.model.Token;
import cn.whuerbbs.backend.model.User;

public interface UserService {

    Token wxLogin(WxLoginDTO wxLoginDTO);

    User getById(String userId);

    void logout(String token);

    int modifySchool(String userId, String school);

    int modifyGradeAndDiploma(String userId, String grade, String diploma);
}
