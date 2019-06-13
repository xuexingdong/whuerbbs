package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.model.PostCollection;
import cn.whuerbbs.backend.service.PostCollectionService;
import cn.whuerbbs.backend.service.PostService;
import cn.whuerbbs.backend.vo.PostListVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("post_collections")
@Validated
public class PostCollectionController {

    @Autowired
    private PostCollectionService postCollectionService;

    @Autowired
    private PostService postService;

    /**
     * 获取收藏列表
     *
     * @param page
     * @param perPage
     * @param currentUserData
     * @return
     */
    @GetMapping
    public Page<PostListVO> getPostCollections(
            @Range(min = 1, max = Integer.MAX_VALUE) @RequestParam(defaultValue = "1") int page,
            @Range(min = 1, max = 100) @RequestParam(value = "per_page", defaultValue = "10") int perPage,
            @CurrentUser CurrentUserData currentUserData) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        var postCollectionPage = postCollectionService.getPageable(currentUserData.getUserId(), pageRequest);
        List<PostListVO> postListVOs = postService.getPostListVO(postCollectionPage.stream().map(PostCollection::getPostId).collect(Collectors.toList()));
        return new PageImpl<>(postListVOs, postCollectionPage.getPageable(), postCollectionPage.getTotalElements());
    }
}
