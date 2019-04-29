package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.CurrentUser;
import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.service.AttachmentService;
import cn.whuerbbs.backend.util.ImageUtil;
import cn.whuerbbs.backend.vo.AttachmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
public class AttachmentControllerV1 {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ImageUtil imageUtil;

    @PostMapping("upload")
    public AttachmentVO upload(@Validated @NotNull @RequestParam MultipartFile file, @CurrentUser CurrentUserData currentUserData) {
        if (file.isEmpty()) {
            throw new BusinessException("文件内容为空");
        }
        var attachment = attachmentService.upload(currentUserData.getUserId(), file);
        var vo = new AttachmentVO();
        vo.setId(attachment.getId());
        vo.setPath(imageUtil.getFullPath(attachment.getPath()));
        return vo;
    }
}
