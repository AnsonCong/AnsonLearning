package com.demo.service;

import com.demo.domain.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anson
 * @date 2019/5/24
 */

@Component
public class GetAnimalService {

    @Autowired
    private Animal animal;

    public void eat() {
        animal.eat();
    }
}
