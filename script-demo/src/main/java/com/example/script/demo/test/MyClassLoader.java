package com.example.script.demo.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MyClassLoader extends ClassLoader {

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!"com.example.script.demo.api.MyObject".equals(name)) {
            return super.loadClass(name);
        }

        try {
            String url = "file:/Users/admin1/IdeaProjects/learnspringboot/script-demo/src/main/java/com/example/script/demo/api/MyObject.class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            System.out.println("------------- load class: " + name);

            return defineClass("com.example.script.demo.api.MyObject",
                    classData, 0, classData.length);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
