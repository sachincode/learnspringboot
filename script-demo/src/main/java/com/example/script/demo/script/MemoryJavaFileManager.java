package com.example.script.demo.script;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    // compiled classes in bytes:
    private final Map<String, byte[]> classBytes = new HashMap<>();

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    public MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    public Map<String, byte[]> getClassBytes() {
        return new HashMap<>(this.classBytes);
    }

    JavaFileObject makeStringSource(String name, String code) {
        return new MemoryInputJavaFileObject(name, code);
    }

    @Override
    public Iterable<JavaFileObject> list(Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
        System.out.println("----------------- MemoryJavaFileManager.list() " + packageName);
        return super.list(location, packageName, kinds, recurse);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        System.out.println("----------------- MemoryJavaFileManager.getJavaFileForOutput() " + className);
        if (kind == JavaFileObject.Kind.CLASS) {
            return new MemoryOutputJavaFileObject(className);
        }
        return super.getJavaFileForOutput(location, className, kind, sibling);
    }

    @Override
    public void flush() throws IOException {
        //super.flush();
        System.out.println("----------------- MemoryJavaFileManager.flush()");
    }

    @Override
    public void close() throws IOException {
        classBytes.clear();
        System.out.println("----------------- MemoryJavaFileManager.close()");
    }

    static class MemoryInputJavaFileObject extends SimpleJavaFileObject {

        final String code;

        MemoryInputJavaFileObject(String name, String code) {
            super(URI.create("string:///" + name), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
            System.out.println("----------------- MemoryInputJavaFileObject.getCharContent()");
            return CharBuffer.wrap(code);
        }

    }

    class MemoryOutputJavaFileObject extends SimpleJavaFileObject {
        final String name;

        MemoryOutputJavaFileObject(String name) {
            super(URI.create("string:///" + name), Kind.CLASS);
            this.name = name;
            System.out.println("----------------- MemoryOutputJavaFileObject.() " + name);
        }

        @Override
        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()) {
                @Override
                public void close() throws IOException {
                    out.close();
                    ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                    classBytes.put(name, bos.toByteArray());
                    System.out.println("----------------- MemoryOutputJavaFileObject.openOutputStream().close()");
                }
            };
        }

    }
}
