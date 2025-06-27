package org.csu.healthsystem.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoginException extends RuntimeException {
    @Getter
    private final String errorCode;
    private final String errorMessage;

    public LoginException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
