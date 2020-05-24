package cn.piggy.mallbackend.common.exception;

import cn.piggy.mallbackend.common.api.IErrorCode;

/**
 * 断言处理类, 用于抛出各种API异常
 *
 * @author IMNOTHD
 * @date 2020/5/23 1:05
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void validateFail(String message) {
        throw new ApiException(new IErrorCode() {
            @Override
            public long getCode() {
                return 400;
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }
}
