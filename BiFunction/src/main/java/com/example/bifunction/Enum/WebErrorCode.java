package com.example.bifunction.Enum;

import org.springframework.http.HttpStatus;

/**
 * @author Anson
 * @date 2019/5/28
 */
public enum WebErrorCode {
    /*
    Define any type of error code here
    * */
    ERROR_FAILED("CODE1111", HttpStatus.BAD_REQUEST.value(), "Failed"),
    ERROR_TIMEOUT("CODE2222", HttpStatus.REQUEST_TIMEOUT.value(), "Connection Timeout"),
    ERROR_BAD_GATEWAY("CODE3333", HttpStatus.BAD_GATEWAY.value(), "Bad Gateway!"),
    ERROR_UNEXPECTED("CODE4444", HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error has occurred!");

    private final String responseCode;
    private final int httpStatusCode;
    private final String responseMessage;

    private WebErrorCode(String responseCode, int httpStatusCode, String responseMessage) {
        this.responseCode = responseCode;
        this.httpStatusCode = httpStatusCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }


    @Override
    public String toString() {
        return String.format("Error Code: [%s], HTTP Status Code: [%d], Error Description: [%s]", this.responseCode, this.httpStatusCode, this.responseMessage);
}
}
