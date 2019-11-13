package com.example.script.demo.script;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.Arrays;

public class Test {


    public static void main(String[] args) {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // /Users/admin1/IdeaProjects/learnspringboot/script-demo/src/main/resources/test/TestClass.java

        String file = "/Users/admin1/IdeaProjects/learnspringboot/script-demo/src/main/java/com/example/script/demo/api/MyObject.java";

         //int run = compiler.run(null, null, null, file);

         //compiler.getTask()

        System.out.println(java.io.File.separator);

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> objects = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(file));



        ClassLoader classLoader = Test.class.getClassLoader();


        System.out.println(System.getProperty("user.dir"));

    }
}
