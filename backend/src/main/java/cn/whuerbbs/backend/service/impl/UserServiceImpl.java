package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.dto.WxLoginDTO;
import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.mapper.UserMapper;
import cn.whuerbbs.backend.mapper.UserWxMapper;
import cn.whuerbbs.backend.model.Token;
import cn.whuerbbs.backend.model.User;
import cn.whuerbbs.backend.model.UserWx;
import cn.whuerbbs.backend.service.JwtService;
import cn.whuerbbs.backend.service.UserService;
import cn.whuerbbs.backend.wx.WxClient;
import cn.whuerbbs.backend.wx.resp.WxLoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserWxMapper userWxMapper;

    @Autowired
    private WxClient wxClient;

    @Autowired
    private JwtService jwtService;

    private User register(WxLoginDTO wxLoginDTO, WxLoginResp resp) {
        // 查询不到用户，则新增
        var user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUnionid(resp.getUnionid());
        user.setLastLoginAt(LocalDateTime.now());
        BeanUtils.copyProperties(wxLoginDTO, user);
        user.setNickname(wxLoginDTO.getNickName());
        var now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setLastLoginAt(now);
        userMapper.insert(user);

        var userWx = new UserWx();
        userWx.setOpenid(resp.getOpenid());
        userWx.setUserId(user.getId());
        userWx.setCreatedAt(now);
        userWxMapper.insert(userWx);
        return user;
    }

    @Override
    public Token wxLogin(WxLoginDTO wxLoginDTO) {
        var respOptional = wxClient.login(wxLoginDTO.getCode());
        if (respOptional.isEmpty()) {
            throw new BusinessException("微信授权出错");
        }
        // TODO 并发问题
        var resp = respOptional.get();
        var userWxOptional = userWxMapper.selectByOpenid(resp.getOpenid());
        String userId;
        if (userWxOptional.isPresent()) {
            userId = userWxOptional.get().getUserId();
        } else {
            userId = register(wxLoginDTO, resp).getId();
        }
        return jwtService.refreshToken(userId);
    }

    @Override
    public Optional<User> getById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public void logout(String token) {
        jwtService.removeToken(token);
    }

    @Override
    public int modifySchool(String userId, String school) {
        var user = new User();
        user.setId(userId);
        user.setSchool(school);
        return userMapper.update(user);
    }

    @Override
    public int modifyGradeAndDiploma(String userId, String grade, String diploma) {
        var user = new User();
        user.setId(userId);
        user.setGrade(grade);
        user.setDiploma(diploma);
        return userMapper.update(user);
    }
}
