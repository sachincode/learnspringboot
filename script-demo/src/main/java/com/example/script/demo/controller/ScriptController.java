package com.example.script.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/script-demo/v1/script/", produces = "application/json")
@Slf4j
public class ScriptController {


    @PostMapping("compile/{name}")
    public Object compile(@PathVariable("name") String name, @RequestBody String code) {

        return null;
    }


    @GetMapping("testGet")
    public void testGet() throws Exception {
        System.out.println("testGet()");

    }


}
