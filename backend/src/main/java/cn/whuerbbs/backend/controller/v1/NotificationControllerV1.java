package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.service.NotificationService;
import cn.whuerbbs.backend.vo.NotificationVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notifications")
@Validated
public class NotificationControllerV1 {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public Page<NotificationVO> getNotifications(
            @Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
            @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
            @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        var notificationPage = notificationService.getPageable(currentUserData.getUserId(), pageRequest);
        return notificationPage.map(notification -> {
            var pair = notificationService.getBoardAndSummary(notification);
            return new NotificationVO(notification, pair.getLeft(), pair.getRight());
        });
    }
}

