package cn.piggy.mallbackend.common.api;

/**
 * @author IMNOTHD
 * @date 2020/5/20 17:16
 */
public enum ResultCode implements IErrorCode {
    // Response Code
    SUCCESS(200, "操作成功"),
    FAILED(500, "服务器错误，请重试"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "登录失效，请重新登录"),
    FORBIDDEN(403, "访问被拒绝");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}