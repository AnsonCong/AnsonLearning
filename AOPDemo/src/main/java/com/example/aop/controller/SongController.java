package com.example.aop.controller;

import com.example.aop.Annotation.LogSong;
import com.example.aop.domain.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anson
 * @date 2019/5/27
 */

@RestController
public class SongController {

    @Autowired
    private Singer singer;

    @RequestMapping("/song")
    @LogSong
    public void chooseSong(@RequestParam("song") String songName) {
        singer.sing(songName);

    }
}
