package com.example.bifunction.pojo;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Anson
 * @date 2019/5/28
 */

@Data
public class RestErrorResponse {

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public RestErrorResponse(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
