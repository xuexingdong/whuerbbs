package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.enumeration.AttitudeStatus;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.service.*;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.PostListVO;
import cn.whuerbbs.backend.vo.PostVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;
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
    private TopicService topicService;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private PostCollectionService postCollectionService;

    @Value("${static-images.default-anonymous-avatar}")
    private String defaultAnonymousAvatarPath;

    @PostMapping("posts")
    public void create(@Validated @RequestBody PostDTO postDTO, @CurrentUser CurrentUserData currentUserData) {
        // 特殊逻辑，走此接口不允许是二手区和匿名
        if (postDTO.getBoard() == Board.ANONYMOUS_POST || postDTO.getBoard() == Board.SECONDHAND) {
            throw new BusinessException("参数错误");
        }
        postService.create(currentUserData.getUserId(), postDTO);
    }

    /**
     * 获取全部帖子（首页、问点事、一起玩共用）
     *
     * @param board
     * @param topicId
     * @param page
     * @param perPage
     * @param currentUserData
     * @return
     */
    @GetMapping("posts")
    public Page<PostListVO> getPosts(
            @RequestParam(required = false) Board board,
            @RequestParam(value = "topic_id", required = false) Long topicId,
            @RequestParam(value = "hot", required = false) boolean hot,
            @Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
            @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
            @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page, perPage);
        Page<Post> postPage;
        // 按版块查询
        if (Objects.nonNull(topicId)) {
            if (hot) {
                postPage = postService.getHotPostsPageableByTopicId(pageRequest, topicId);
            } else {
                postPage = postService.getPostsPageableByTopicId(pageRequest, topicId);
            }
        } else {
            postPage = postService.getPageableByBoard(pageRequest, board);
        }
        return postPage.map(postService::getPostListVO);
    }

    @GetMapping("posts/{postId}")
    public PostVO getPostDetail(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        var postOptional = postService.getById(postId);
        var post = postOptional.orElseThrow(() -> new BusinessException("帖子不存在"));
        var attachments = attachmentService.getByPostId(post.getId());
        var topics = topicService.getTopicsByPostId(post.getId());
        // 匿名帖子要特殊处理
        if (post.getBoard() == Board.ANONYMOUS_POST) {
            post = postService.addAnonymousInfo(post);
        }
        var collected = postCollectionService.hasCollected(currentUserData.getUserId(), postId);
        var postVO = new PostVO(post, attachments.stream().map(attachment -> imageUtil.getFullPath(attachment.getPath())).collect(Collectors.toList()), topics, collected, currentUserData);
        postVO.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId)));
        return postVO;
    }

    @DeleteMapping("posts/{postId}")
    public void delete(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        postService.deleteById(postId);
    }

    @GetMapping("posts/{postId}/like")
    public void like(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.add(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId), AttitudeStatus.LIKE);
    }

    @GetMapping("posts/{postId}/cancel_like")
    public void cancelLike(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        attitudeService.delete(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId));
    }

    /**
     * 收藏帖子
     *
     * @param postId
     * @param currentUserData
     */
    @GetMapping("posts/{postId}/collect")
    public void collect(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        postCollectionService.collect(currentUserData.getUserId(), postId);
    }

    /**
     * 取消收藏帖子
     *
     * @param postId
     * @param currentUserData
     */
    @GetMapping("posts/{postId}/cancel_collect")
    public void cancelCollect(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        postCollectionService.cancelCollect(currentUserData.getUserId(), postId);
    }
}
