package com.weixf.grace.result;

import lombok.Getter;

/*
 *
 * @author weixf
 * @date 2022-06-07
 */
@Getter
public enum ResultCode implements StatusCode {

    SUCCESS(1000, "请求成功"),
    FAILED(1001, "请求失败"),
    VALIDATE_ERROR(1002, "参数校验失败"),
    RESPONSE_PACK_ERROR(1003, "response返回包装失败");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
