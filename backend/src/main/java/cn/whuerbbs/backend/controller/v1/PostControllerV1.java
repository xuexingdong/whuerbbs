package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.service.AttachmentService;
import cn.whuerbbs.backend.service.AttitudeService;
import cn.whuerbbs.backend.service.PostService;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.PostListVO;
import cn.whuerbbs.backend.vo.PostVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;


@RestController
@Validated
public class PostControllerV1 {

    @Autowired
    private PostService postService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttitudeService attitudeService;

    @Autowired
    private ImageUtil imageUtil;

    @PostMapping("posts")
    public void create(@Validated @RequestBody PostDTO postDTO, @CurrentUser CurrentUserData currentUserData) {
        postService.create(currentUserData.getUserId(), postDTO);
    }

    @GetMapping("allposts")
    public Page<PostListVO> getAllPosts(
            @Validated @Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
            @Validated @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
            @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page - 1, perPage, Sort.by("lastActiveAt").descending());
        var postPage = postService.getPageable(pageRequest);
        return postPage.map(post -> {
            var attachmentOptional = attachmentService.getFirstByPostId(post.getId());
            return new PostListVO(post, attachmentOptional.map(attachment -> imageUtil.getFullPath(attachment.getPath())).orElse(null));
        });
    }

    @GetMapping("posts/{postId}")
    public PostVO getPostDetail(@NotNull @PathVariable long postId, @CurrentUser CurrentUserData currentUserData) {
        var postOptional = postService.getById(postId);
        var post = postOptional.orElseThrow(() -> new BusinessException("帖子不存在"));
        var attachments = attachmentService.getByPostId(post.getId());
        var postVO = new PostVO(post, attachments.stream().map(attachment -> imageUtil.getFullPath(attachment.getPath())).collect(Collectors.toList()));
        postVO.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId)));
        return postVO;
    }

    @GetMapping("posts/{postId}/like")
    public void like(@PathVariable long postId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.add(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId), AttitudeStatus.LIKE);
    }

    @GetMapping("posts/{postId}/cancel_like")
    public void cancelLike(@PathVariable long postId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.delete(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId));
    }
}
