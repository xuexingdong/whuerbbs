package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.service.AttachmentService;
import cn.whuerbbs.backend.service.TopicService;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.TopicDetailVO;
import cn.whuerbbs.backend.vo.TopicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("topics")
@Validated
public class TopicControllerV1 {

    @Autowired
    private TopicService topicService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping
    public List<TopicVO> getTopicsByBoard(@NotNull @RequestParam Board board) {
        return topicService.getTopicsByBoard(board).stream().map(TopicVO::new).collect(Collectors.toList());
    }

    /**
     * 话题详情
     *
     * @param topicId
     * @return
     */
    @GetMapping("{topicId}")
    public TopicDetailVO getTopicsByBoard(@PathVariable int topicId) {
        var topicOptional = topicService.getTopicsById(topicId);
        var topic = topicOptional.orElseThrow(() -> new BusinessException("帖子不存在"));
        var pair = topicService.getStatistics(topicId);
        var attachmentOptional = attachmentService.getById(topic.getAttachmentId());
        return new TopicDetailVO(topic, attachmentOptional.map(attachment -> imageUtil.getFullPath(attachment.getPath())).orElse(null),
                pair.getLeft(), pair.getRight());
    }
}
