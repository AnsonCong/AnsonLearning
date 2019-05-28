package com.demo.config;

import com.demo.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anson
 * @date 2019/5/24
 */

@Configuration
public class PersonConfig {

    /*
    *   将bean注入到容器中
    * */

    @Bean
    public Person person(){
        return new Person();
    }
}
