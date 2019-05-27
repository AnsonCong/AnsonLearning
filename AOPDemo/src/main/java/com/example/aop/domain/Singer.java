package com.example.aop.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Anson
 * @date 2019/5/27
 */

@Data
@Component
public class Singer {

    private String song;
    public Singer() {
     System.out.println("the Singer will sing a song");
    }

    public void sing(String song){
        System.out.println("the Singer is Singing "+ song);
    }

}
