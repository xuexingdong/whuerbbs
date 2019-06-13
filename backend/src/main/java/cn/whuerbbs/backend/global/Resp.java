package cn.whuerbbs.backend.global;

import org.springframework.http.HttpStatus;

public final class Resp<T> {

    private int code;
    private String message;
    private T data;

    private Resp() {

    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static Resp<Void> ok() {
        return ok(HttpStatus.OK.name());
    }

    public static Resp<Void> ok(String message) {
        var resp = new Resp<Void>();
        resp.message = message;
        return resp;
    }

    public static <T> Resp<T> data(T data) {
        var resp = new Resp<T>();
        resp.message = HttpStatus.OK.name();
        resp.data = data;
        return resp;
    }

    public static Resp badRequest() {
        return badRequest(HttpStatus.BAD_REQUEST.name());
    }

    public static Resp badRequest(String message) {
        var resp = new Resp();
        resp.code = HttpStatus.BAD_REQUEST.value();
        resp.message = message;
        return resp;
    }

    public static Resp error(String message) {
        var resp = new Resp();
        resp.code = -1;
        resp.message = message;
        return resp;
    }

    public static Resp internalServerError() {
        return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }

    public static Resp internalServerError(String message) {
        var resp = new Resp();
        resp.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        resp.message = message;
        return resp;
    }

    public Resp message(String message) {
        this.message = message;
        return this;
    }
}
