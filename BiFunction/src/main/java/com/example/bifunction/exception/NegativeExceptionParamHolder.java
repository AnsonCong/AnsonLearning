package com.example.bifunction.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Anson
 * @date 2019/5/28
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NegativeExceptionParamHolder {

    private String correlationId;

    private WebRestfulException e;
}
