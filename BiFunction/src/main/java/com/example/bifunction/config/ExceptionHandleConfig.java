package com.example.bifunction.config;

import com.example.bifunction.Enum.WebErrorCode;
import com.example.bifunction.exception.NegativeExceptionParamHolder;
import com.example.bifunction.compent.RestErrorResponseBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.BiFunction;

/**
 * @author Anson
 * @date 2019/5/28
 */

@Configuration
public class ExceptionHandleConfig {

    @Bean("TimeOutResponseFunction")
    public BiFunction<NegativeExceptionParamHolder, RestErrorResponseBuilder, ResponseEntity> handleTimeOut() {
        return new BiFunction<NegativeExceptionParamHolder, RestErrorResponseBuilder, ResponseEntity>() {
            @Override
            public ResponseEntity apply(NegativeExceptionParamHolder parameterHolder, RestErrorResponseBuilder restErrorResponseBuilder) {
                // u can define the builder to do anything
                return restErrorResponseBuilder.buildRestErrorResponse(parameterHolder.getE().getErrorCode(),
                        "TimeOut: " + parameterHolder.getE().getErrorCode().getResponseMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
