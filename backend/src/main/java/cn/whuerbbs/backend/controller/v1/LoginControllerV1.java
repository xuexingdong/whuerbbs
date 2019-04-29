package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.WxLoginDTO;
import cn.whuerbbs.backend.model.Token;
import cn.whuerbbs.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControllerV1 {

    @Autowired
    private UserService userService;

    @PostMapping("/wxlogin")
    public Token wxLogin(@Validated @RequestBody WxLoginDTO wxLoginDTO) {
        return userService.wxLogin(wxLoginDTO);
    }

    @PostMapping("/logout")
    public void logout(@CurrentUser CurrentUserData currentUserData) {
        userService.logout(currentUserData.getToken());
    }
}
