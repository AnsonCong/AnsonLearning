package com.demo.domain;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Anson
 * @date 2019/5/24
 */
@Configuration
@Data
@Component
@Profile("Cat")
public class Cat implements Animal {
    public Cat() {
        System.out.println("Cat generate");
    }


    @Override
    public void eat() {
        System.out.println("Cat eat mouse");
    }
}

