package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.AnonymousPostDTO;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.exception.NotExistsException;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.service.*;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.AnonymousPostListVO;
import cn.whuerbbs.backend.vo.AnonymousPostVO;
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
@RequestMapping("anonymous_posts")
@Validated
public class AnonymousPostControllerV1 {

    @Autowired
    private PostService postService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AnonymousPostService anonymousPostService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private AttitudeService attitudeService;

    @Autowired
    private PostCollectionService postCollectionService;

    @Value("${static-images.default-anonymous-avatar}")
    private String defaultAnonymousAvatarPath;

    @PostMapping
    public void publish(@Validated @RequestBody AnonymousPostDTO anonymousPostDTO, @CurrentUser CurrentUserData currentUserData) {
        anonymousPostService.publish(currentUserData.getUserId(), anonymousPostDTO);
    }

    /**
     * 获取所有匿名帖子
     *
     * @param page
     * @param perPage
     * @param currentUserData
     * @return
     */
    @GetMapping
    public Page<AnonymousPostListVO> getAllAnonymousPosts(
            @RequestParam(value = "topic_id", required = false) Long topicId,
            @RequestParam(value = "hot", required = false) boolean hot,
            @Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
            @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
            @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        Page<Post> postPage;
        if (Objects.nonNull(topicId)) {
            if (hot) {
                postPage = postService.getHotPostsPageableByTopicId(topicId, pageRequest);
            } else {
                postPage = postService.getPostsPageableByTopicId(topicId, pageRequest);
            }
        } else {
            postPage = postService.getPageableByBoard(Board.ANONYMOUS_POST, pageRequest);
        }
        return postPage.map(post -> {
            var attachmentOptional = attachmentService.getFirstByPostId(post.getId());
            var anonymousPost = anonymousPostService.getByPostId(post.getId()).orElseThrow(() -> new NotExistsException("帖子不存在"));
            var topics = topicService.getTopicsByPostId(post.getId());
            return new AnonymousPostListVO(post,
                    attachmentOptional.map(attachment -> imageUtil.getFullPath(attachment.getPath())).orElse(null),
                    topics, anonymousPost.getAnonymousName(),
                    imageUtil.getFullPath(defaultAnonymousAvatarPath));
        });
    }

    @GetMapping("{postId}")
    public AnonymousPostVO getAnymousPostDetail(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        var post = postService.getById(postId);
        var attachments = attachmentService.getByPostId(post.getId());
        var topics = topicService.getTopicsByPostId(post.getId());
        var collected = postCollectionService.hasCollected(currentUserData.getUserId(), postId);
        var anonymousPost = anonymousPostService.getByPostId(post.getId()).orElseThrow(() -> new NotExistsException("帖子不存在"));
        var anonymousPostVO = new AnonymousPostVO(post,
                attachments.stream().map(attachment -> imageUtil.getFullPath(attachment.getPath())).collect(Collectors.toList()),
                topics,
                collected,
                currentUserData,
                anonymousPost.getAnonymousName(),
                imageUtil.getFullPath(defaultAnonymousAvatarPath));
        anonymousPostVO.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId)));
        return anonymousPostVO;
    }
}
