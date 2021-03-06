package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.dto.SecondhandPostDTO;
import cn.whuerbbs.backend.enumeration.AttitudeTarget;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.exception.NotExistsException;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.service.*;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.SecondhandPostListVO;
import cn.whuerbbs.backend.vo.SecondhandPostVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("secondhand_posts")
@Validated
public class SecondhandPostControllerV1 {

    @Autowired
    private SecondhandPostService secondhandPostService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PostService postService;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private TopicService topicService;

    @Autowired
    private AttitudeService attitudeService;

    @Autowired
    private PostCollectionService postCollectionService;

    /**
     * 发布二手区帖子
     *
     * @param secondhandPostDTO
     * @param currentUserData
     */
    @PostMapping
    public void publish(@Validated @RequestBody SecondhandPostDTO secondhandPostDTO, @CurrentUser CurrentUserData currentUserData) {
        secondhandPostService.publish(currentUserData.getUserId(), secondhandPostDTO);
    }

    /**
     * 获取所有二手区帖子
     *
     * @param page
     * @param perPage
     * @param currentUserData
     * @return
     */
    @GetMapping
    public Page<SecondhandPostListVO> getAllSecondhandPosts(
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
            postPage = postService.getPageableByBoard(Board.SECONDHAND, pageRequest);
        }
        return postPage.map(post -> {
            var attachmentOptional = attachmentService.getFirstByPostId(post.getId());
            var secondhandPost = secondhandPostService.getByPostId(post.getId()).orElseThrow(() -> new NotExistsException("帖子不存在"));
            var topics = topicService.getTopicsByPostId(post.getId());
            return new SecondhandPostListVO(post, attachmentOptional.map(attachment -> imageUtil.getFullPath(attachment.getPath())).orElse(null), topics, secondhandPost.getTradeCategory(), secondhandPost.getCampus());
        });
    }

    @GetMapping("{postId}")
    public SecondhandPostVO getSecondhandPostDetail(@NotNull @PathVariable Long postId, @CurrentUser CurrentUserData currentUserData) {
        var post = postService.getById(postId);
        var attachments = attachmentService.getByPostId(post.getId());
        var topics = topicService.getTopicsByPostId(post.getId());
        var secondhandPost = secondhandPostService.getByPostId(post.getId()).orElseThrow(() -> new NotExistsException("帖子不存在"));
        var collected = postCollectionService.hasCollected(currentUserData.getUserId(), postId);
        var secondhandPostVO = new SecondhandPostVO(post, attachments.stream().map(attachment -> imageUtil.getFullPath(attachment.getPath())).collect(Collectors.toList()), topics, collected, currentUserData, secondhandPost.getTradeCategory(), secondhandPost.getCampus());
        secondhandPostVO.setAttitudeStatus(attitudeService.getAttitudeStatus(currentUserData.getUserId(), AttitudeTarget.POST, String.valueOf(postId)));
        return secondhandPostVO;
    }
}
