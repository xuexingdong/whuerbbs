package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.CommentDTO;
import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.service.AttitudeService;
import cn.whuerbbs.backend.service.CommentService;
import cn.whuerbbs.backend.vo.CommentVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
@Validated
public class CommentControllerV1 {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AttitudeService attitudeService;

    @GetMapping
    public Page<CommentVO> getComments(@Validated @Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
                                       @Validated @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
                                       @RequestParam("post_id") long postId,
                                       @CurrentUser CurrentUserData currentUserData) {
        // TODO 鉴权
        var pageRequest = PageRequest.of(page, perPage, Sort.by("createdAt").descending());
        var pageable = commentService.getPageable(postId, pageRequest);
        return pageable.map(comment -> {
            var vo = new CommentVO(comment);
            vo.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(comment.getId())));
            return vo;
        });
    }

    @PostMapping
    public void addComment(@RequestBody CommentDTO commentDTO, @CurrentUser CurrentUserData currentUserData) {
        commentService.commentPost(currentUserData.getUserId(), commentDTO);
    }

    @GetMapping("{commentId}/like")
    public void like(@PathVariable long commentId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.add(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(commentId), AttitudeStatus.LIKE);
    }

    @GetMapping("{commentId}/cancel_like")
    public void cancel(@PathVariable long commentId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.delete(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(commentId));
    }
}
