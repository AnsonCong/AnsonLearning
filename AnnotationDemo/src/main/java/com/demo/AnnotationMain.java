package com.demo;

import com.demo.config.PersonConfig;
import com.demo.domain.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Anson
 * @date 2019/5/24
 */
public class AnnotationMain {

    public static void main (String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(PersonConfig.class);
        Person person = (Person)context.getBean("person");
    }
}
