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
@Profile("Dog")
public class Dog implements Animal {
    public Dog() {
        System.out.println("Dog generated...");
    }

    @Override
    public void eat() {
        System.out.println("Dog eat bone");
    }
}
