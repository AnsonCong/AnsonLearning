package com.example.demo.service;

import com.example.demo.domain.Users;

import javax.batch.api.chunk.ItemProcessor;

/**
 * @author Anson
 * @date 2019/6/9
 */
public class RowToDataClientInfoProcessor implements ItemProcessor<String[], Users> {
    @Override
    public Object processItem(Object o) throws Exception {
        return null;
    }
}
