package com.example.bifunction.exception;

import com.example.bifunction.Enum.WebErrorCode;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @author Anson
 * @date 2019/5/28
 */
public class WebRestfulException extends RuntimeException {

    private static final long serialVersionUID = 4739210739820130302L;
    private WebErrorCode errorCode;

    public WebRestfulException() {
    }

    public WebRestfulException(WebErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public WebErrorCode getErrorCode() {
        return this.errorCode;
    }

}
