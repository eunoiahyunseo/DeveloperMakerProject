package com.hyunseo.programming.dmaker.exception;

/**
 * @author ihyeonseo
 * @created 26/12/2022 - 7:41 오후
 * @project dmaker
 */
public class DMakerException extends RuntimeException {
    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    public DMakerException(DMakerErrorCode errorCode) {
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DMakerException(DMakerErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }
}
