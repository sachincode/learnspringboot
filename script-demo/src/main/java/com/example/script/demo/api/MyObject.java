package com.example.script.demo.api;
import com.example.script.demo.api.MyInterface;

public class MyObject implements MyInterface {
    @Override
    public void sayHi() {
        System.out.println("hello my object");
    }
}
