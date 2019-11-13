package com.example.script.demo.script;

import com.example.script.demo.api.MyInterface;

import java.io.IOException;
import java.util.Map;

public class TestC {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {




        String code = "package com.example.script.demo.api;\n" +
                "import com.example.script.demo.api.MyInterface;\n" +
                "\n" +
                "public class MyObject1 implements MyInterface {\n" +
                "    @Override\n" +
                "    public void sayHi() {\n" +
                "        System.out.println(\"hello my object\");\n" +
                "    }\n" +
                "}";


        String code2 = "package com.example.script.demo.api;\n" +
                "import com.example.script.demo.api.MyInterface;\n" +
                "\n" +
                "public class MyObject1 implements MyInterface {\n" +
                "    @Override\n" +
                "    public void sayHi() {\n" +
                "        System.out.println(\"hello my object 2222\");\n" +
                "    }\n" +
                "}";



        StringJavaCompiler compiler = new StringJavaCompiler();

        Map<String, byte[]> classMap = compiler.compile("MyObject1.java", code);
        for (Map.Entry<String, byte[]> entry : classMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().length);
        }

        Class<?> myObject1 = compiler.loadClass("com.example.script.demo.api.MyObject1", classMap);
        MyInterface instance = (MyInterface) myObject1.newInstance();
        instance.sayHi();


        classMap = compiler.compile("MyObject1.java", code2);
        myObject1 = compiler.loadClass("com.example.script.demo.api.MyObject1", classMap);
        instance = (MyInterface) myObject1.newInstance();
        instance.sayHi();

    }
}
