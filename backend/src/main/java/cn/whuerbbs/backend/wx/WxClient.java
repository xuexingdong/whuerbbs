package cn.whuerbbs.backend.wx;

import cn.whuerbbs.backend.config.WxConfig;
import cn.whuerbbs.backend.wx.resp.WxLoginResp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Component
public class WxClient {
    private static final Logger logger = LoggerFactory.getLogger(WxClient.class);
    private static final String WX_PREFIX = "https://api.weixin.qq.com";
    private static final String WX_LOGIN = WX_PREFIX + "/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient client;

    public Optional<WxLoginResp> login(String code) {

        var url = String.format(WX_LOGIN, wxConfig.getAppId(), wxConfig.getAppSecret(), code);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(3))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
        WxLoginResp resp;
        try {
            resp = objectMapper.readValue(response.body(), WxLoginResp.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
        if (Objects.nonNull(resp.getErrcode()) && resp.getErrcode().equals("0")) {
            logger.error("Wechat auth failed with code: {}, errcode: {}, errmsg: {}", code, resp.getErrcode(), resp.getErrmsg());
            return Optional.empty();
        }
        return Optional.of(resp);
    }
}
