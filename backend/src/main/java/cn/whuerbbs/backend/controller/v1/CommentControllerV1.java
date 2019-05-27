package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.CommentDTO;
import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.model.Comment;
import cn.whuerbbs.backend.service.AttitudeService;
import cn.whuerbbs.backend.service.CommentService;
import cn.whuerbbs.backend.vo.CommentListVO;
import cn.whuerbbs.backend.vo.CommentVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class CommentControllerV1 {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AttitudeService attitudeService;

    /**
     * 删除评论
     *
     * @param commentId
     * @param currentUserData
     */
    @DeleteMapping("comments/{commentId}")
    public void deleteComment(@NotNull @PathVariable Long commentId,
                              @CurrentUser CurrentUserData currentUserData) {
        commentService.deleteCommentById(commentId);
    }

    /**
     * 查看评论详情
     *
     * @param commentId
     * @param currentUserData
     * @return
     */
    @GetMapping("comments/{commentId}")
    public CommentListVO getCommentDetail(@NotNull @PathVariable Long commentId,
                                          @CurrentUser CurrentUserData currentUserData) {
        var comment = commentService.getCommentById(commentId);
        return getCommentDetailVO(comment, currentUserData);
    }

    /**
     * 获取帖子评论列表
     *
     * @param page
     * @param perPage
     * @param postId
     * @param currentUserData
     * @return
     */
    @GetMapping("comments")
    public Page<CommentListVO> getComments(@Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
                                           @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
                                           @NotNull @RequestParam("post_id") Long postId,
                                           @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page, perPage);
        var commentPage = commentService.getPageable(postId, pageRequest);
        return commentPage.map(comment -> getCommentListVO(comment, currentUserData));
    }

    /**
     * 获取评论的子评论列表
     *
     * @param page
     * @param perPage
     * @param commentId
     * @param currentUserData
     * @return
     */
    @GetMapping("sub_comments")
    public Page<CommentVO> getSubComments(@Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
                                          @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
                                          @NotNull @RequestParam("comment_id") Long commentId,
                                          @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page, perPage);
        var pageable = commentService.getSubCommentsPageable(commentId, pageRequest);
        return pageable.map(comment -> getCommentVO(comment, currentUserData));
    }

    @GetMapping("hot_comments")
    public List<CommentListVO> getHotComments(@NotNull @RequestParam("post_id") Long postId, @CurrentUser CurrentUserData currentUserData) {
        return commentService.getHotComments(postId).stream().map(comment -> getCommentListVO(comment, currentUserData)).collect(Collectors.toList());
    }

    @PostMapping("comments")
    public void addComment(@Validated @RequestBody CommentDTO commentDTO, @CurrentUser CurrentUserData currentUserData) {
        commentService.commentPost(currentUserData.getUserId(), commentDTO);
    }

    @GetMapping("comments/{commentId}/like")
    public void like(@NotNull @PathVariable Long commentId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.add(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(commentId), AttitudeStatus.LIKE);
    }

    @GetMapping("comments/{commentId}/cancel_like")
    public void cancel(@NotNull @PathVariable Long commentId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.delete(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(commentId));
    }

    private CommentListVO getCommentListVO(Comment comment, CurrentUserData currentUserData) {
        var subCommentPage = commentService.getSubCommentsPageable(comment.getId(), PageRequest.of(1, 2));
        var subCommentsVO = subCommentPage.stream().map(c -> this.getCommentVO(c, currentUserData)).collect(Collectors.toList());
        var vo = new CommentListVO(comment, subCommentsVO, subCommentPage.getTotalElements(), currentUserData);
        vo.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(comment.getId())));
        return vo;
    }

    private CommentListVO getCommentDetailVO(Comment comment, CurrentUserData currentUserData) {
        var subComments = commentService.getAllSubComments(comment.getId());
        var subCommentsVO = subComments.stream().map(c -> this.getCommentVO(c, currentUserData)).collect(Collectors.toList());
        var vo = new CommentListVO(comment, subCommentsVO, subCommentsVO.size(), currentUserData);
        vo.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(comment.getId())));
        return vo;
    }

    private CommentVO getCommentVO(Comment comment, CurrentUserData currentUserData) {
        var vo = new CommentVO(comment, currentUserData);
        vo.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.COMMENT, String.valueOf(comment.getId())));
        return vo;
    }
}
