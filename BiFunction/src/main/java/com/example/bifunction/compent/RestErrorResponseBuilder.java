package com.example.bifunction.compent;

import com.example.bifunction.Enum.WebErrorCode;
import com.example.bifunction.pojo.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Anson
 * @date 2019/5/28
 */

@Component
public class RestErrorResponseBuilder {

    public ResponseEntity buildRestErrorResponse(WebErrorCode webErrorCode,
                                                 String message,
                                                 HttpStatus status) {
        return new ResponseEntity(
                new RestErrorResponse(webErrorCode.getResponseCode(), message,status)
                ,status);

    }
}
