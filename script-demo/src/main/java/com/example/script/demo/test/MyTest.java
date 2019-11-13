package com.example.script.demo.test;

import com.example.script.demo.api.MyInterface;

/**
 * https://segmentfault.com/a/1190000016703022?utm_source=tag-newest
 */
public class MyTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass("com.example.script.demo.api.MyObject");

        MyInterface object1 = (MyInterface) myObjectClass.newInstance();

        object1.sayHi();
        System.out.println(object1);


        //create new class loader so classes can be reloaded.
        classLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = classLoader.loadClass("com.example.script.demo.api.MyObject");

        object1 = (MyInterface)       myObjectClass.newInstance();

        object1.sayHi();
        System.out.println(object1);

    }
}
