package com.example.bifunction.controller;

import com.example.bifunction.Enum.WebErrorCode;
import com.example.bifunction.compent.RestErrorResponseBuilder;
import com.example.bifunction.exception.NegativeExceptionParamHolder;
import com.example.bifunction.exception.WebRestfulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.function.BiFunction;

/**
 * @author Anson
 * @date 2019/5/28
 */
public class TestExceptionController {

    private String correlationId = "1231231231";

    private BiFunction<NegativeExceptionParamHolder, RestErrorResponseBuilder, ResponseEntity> handleTimeOut;
    private RestErrorResponseBuilder restErrorResponseBuilder;

    @Autowired
    public TestExceptionController(@Qualifier(value = "TimeOutResponseFunction") BiFunction<NegativeExceptionParamHolder, RestErrorResponseBuilder, ResponseEntity> handleTimeOut,
                                   RestErrorResponseBuilder restErrorResponseBuilder) {
        this.handleTimeOut = handleTimeOut;
        this.restErrorResponseBuilder = restErrorResponseBuilder;
    }

    @RequestMapping("/test")
    public ResponseEntity<?>  testMethod() {
        System.out.println("test method happen");
        try {
            throwExceptionMethod();
        } catch (WebRestfulException e) {
            // u can define any handle as business requirement
            return this.timeoutErrorHandle(correlationId,e);
        }
        return null;
    }

    public ResponseEntity timeoutErrorHandle(String correlationId, WebRestfulException e) {
        System.out.println("TIME_OUT");
        return this.handleTimeOut.apply(new NegativeExceptionParamHolder(correlationId,e),
                restErrorResponseBuilder);
    }

    private void throwExceptionMethod() {
        throw new WebRestfulException(WebErrorCode.ERROR_TIMEOUT);
    }
}
