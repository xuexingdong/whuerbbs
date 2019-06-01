package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.common.SchoolConstants;
import cn.whuerbbs.backend.dto.ModifyGradeDTO;
import cn.whuerbbs.backend.dto.ModifySchoolDTO;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.service.NotificationService;
import cn.whuerbbs.backend.service.UserService;
import cn.whuerbbs.backend.vo.SelfUserDetailVO;
import cn.whuerbbs.backend.vo.UserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Validated
public class UserControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user")
    public SelfUserDetailVO getSelfProfile(@CurrentUser CurrentUserData currentUserData) {
        var user = userService.getById(currentUserData.getUserId());
        long unreadCount = notificationService.countUnreadByUserId(user.getId());
        return new SelfUserDetailVO(user, unreadCount);
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
    public UserDetailVO getUserProfile(@NotNull @PathVariable String userId, @CurrentUser CurrentUserData currentUserData) {
        var user = userService.getById(userId);
        return new UserDetailVO(user);
    }
}
