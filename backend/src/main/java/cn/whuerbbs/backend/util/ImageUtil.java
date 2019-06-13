package cn.whuerbbs.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Objects;

@Component
public class ImageUtil {

    @Value("${host}")
    private String serverAddress;

    public String getFullPath(String path) {
        if (Objects.isNull(path)) {
            return null;
        }
        return "https://" + Paths.get(serverAddress, path).toString();
    }
}
