package cn.whuerbbs.backend.exception;

public abstract class BaseException extends RuntimeException {
    protected int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
