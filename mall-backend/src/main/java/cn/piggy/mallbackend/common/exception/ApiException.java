package cn.piggy.mallbackend.common.exception;

import cn.piggy.mallbackend.common.api.IErrorCode;

/**
 * 自定义API异常
 *
 * @author IMNOTHD
 * @date 2020/5/23 1:04
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
