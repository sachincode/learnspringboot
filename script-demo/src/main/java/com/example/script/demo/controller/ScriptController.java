package com.example.script.demo.controller;

import com.example.script.demo.shortlink.ShortLink;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(description = "java动态编译")
@RestController
@RequestMapping(value = "/script-demo/v1/script/", produces = "application/json")
@Slf4j
public class ScriptController {


    @Autowired
    private ApplicationContext context;


    @PostMapping("compile/{name}")
    public Object compile(@PathVariable("name") String name, @RequestBody String code) {

        return "abc";
    }


    @GetMapping("testGet")
    public Object testGet() throws Exception {
        System.out.println("testGet()");

        return new ShortLink("abc", new Date());
    }


}
