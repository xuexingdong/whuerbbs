package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.service.NotificationService;
import cn.whuerbbs.backend.service.UserService;
import cn.whuerbbs.backend.vo.SelfUserVO;
import cn.whuerbbs.backend.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user")
    public SelfUserVO getSelfProfile(@CurrentUser CurrentUserData currentUserData) {
        var userOptional = userService.getById(currentUserData.getUserId());
        var user = userOptional.orElseThrow(() -> new BusinessException("用户不存在"));
        long unreadCount = notificationService.countUnreadByUserId(user.getId());
        return new SelfUserVO(user, unreadCount);
    }

    @GetMapping("/users/{userId}")
    public UserVO getUserProfile(@PathVariable String userId, @CurrentUser CurrentUserData currentUserData) {
        var userOptional = userService.getById(userId);
        var user = userOptional.orElseThrow(() -> new BusinessException("用户不存在"));
        return new UserVO(user);
    }
}
