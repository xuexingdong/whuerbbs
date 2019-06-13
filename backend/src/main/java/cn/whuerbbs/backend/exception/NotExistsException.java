package cn.whuerbbs.backend.exception;

public class NotExistsException extends BaseException {

    public NotExistsException(String message) {
        super(-1, message);
    }
}
