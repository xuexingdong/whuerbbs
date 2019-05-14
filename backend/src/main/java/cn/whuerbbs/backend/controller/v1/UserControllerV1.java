package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.common.SchoolConstants;
import cn.whuerbbs.backend.dto.ModifyGradeDTO;
import cn.whuerbbs.backend.dto.ModifySchoolDTO;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.service.NotificationService;
import cn.whuerbbs.backend.service.UserService;
import cn.whuerbbs.backend.vo.SelfUserVO;
import cn.whuerbbs.backend.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/user/school")
    public void modifySchoolInfo(@Validated @RequestBody ModifySchoolDTO modifySchoolDTO, @CurrentUser CurrentUserData currentUserData) {
        if (!SchoolConstants.SCHOOL_LIST.contains(modifySchoolDTO.getSchool())) {
            throw new BusinessException("非法参数");
        }
        userService.modifySchool(currentUserData.getUserId(), modifySchoolDTO.getSchool());
    }

    @PutMapping("/user/grade")
    public void modifyGradeInfo(@Validated @RequestBody ModifyGradeDTO modifyGradeDTO, @CurrentUser CurrentUserData currentUserData) {
        var index1 = SchoolConstants.GRADE_LIST.contains(modifyGradeDTO.getGrade());
        var index2 = SchoolConstants.DIPLOMA_LIST.contains(modifyGradeDTO.getDiploma());
        if (index1 && index2) {
            userService.modifyGradeAndDiploma(currentUserData.getUserId(), modifyGradeDTO.getGrade(), modifyGradeDTO.getDiploma());
        } else {
            throw new BusinessException("非法参数");
        }
    }

    @GetMapping("/users/{userId}")
    public UserVO getUserProfile(@PathVariable String userId, @CurrentUser CurrentUserData currentUserData) {
        var userOptional = userService.getById(userId);
        var user = userOptional.orElseThrow(() -> new BusinessException("用户不存在"));
        return new UserVO(user);
    }
}
