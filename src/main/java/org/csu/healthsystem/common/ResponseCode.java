package org.csu.healthsystem.common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0, "Success"),
    ERROR(1, "Error"),
    NEED_LOGIN(11, "Need Login"),
    ILLEGAL_ARGUMENT(10, "Illegal Argument"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 通过 code 获取 ResponseCode
    public static ResponseCode fromCode(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.code == code) {
                return responseCode;
            }
        }
        return ERROR;
    }
}
