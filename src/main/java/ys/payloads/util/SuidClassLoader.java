package ys.payloads.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * author: c0ny1
 * 打破双亲委派
 * 能传入base64Jar和base64Class
 */
public class SuidClassLoader extends ClassLoader {
    public static SuidClassLoader instanse = new SuidClassLoader();
    private final Map<String, Class> cacheClass = new ConcurrentHashMap<String, Class>();
    private Map<String, byte[]> classByteMap = new HashMap<String,byte[]>();

    public SuidClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // 1. 检测自定ClassLoader缓存中有没有，有的话直接返回
        Class clazz = cacheClass.get(name);
        if (null != clazz) {
            return clazz;
        }

        try {
            // 2. 若缓存中没有，就从当前ClassLoader可加载的所有Class中找
            clazz = findClass(name);
            if (null != clazz) {
                cacheClass.put(name, clazz);
            }else{
                clazz = super.loadClass(name, resolve);
            }
        } catch (ClassNotFoundException ex) {
            // 3.当自定义ClassLoader中没有找到目标class，再调用系统默认的加载机制,走双亲委派模式
            clazz = super.loadClass(name, resolve);
        }

        if (resolve) {
            resolveClass(clazz);
        }
        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] result = classByteMap.get(name);
        if(result == null){
            throw new ClassNotFoundException();
        }else{
            return defineClass(name, result, 0, result.length);
        }
    }

    public void cleanLoader() {
        instanse = new SuidClassLoader();
        cacheClass.clear();
        classByteMap.clear();
    }


    public void addClass(String className, byte[] classByte){
        classByteMap.put(className,classByte);
    }

    public void addJar(byte[] byteJar){
        try {
            final File tempFile = File.createTempFile("Temp", ".jar");
            tempFile.deleteOnExit();
            writeBytesToFile(tempFile.getAbsolutePath(), byteJar);
            String jarPath = tempFile.getAbsolutePath();
            this.addJar(jarPath);
            //tempFile.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加一个jar包到加载器中去。
     * @param jarPath
     * @throws IOException
     */
    public void addJar(String jarPath) throws IOException {
        File file = new File(jarPath);
        if(file.exists()){
            JarFile jar = new JarFile(file);
            this.readJAR(jar);
        }
    }

    /**
     * 读取一个jar包内的class文件，并存在当前加载器的map中
     * @param jar
     * @throws IOException
     */
    private void readJAR(JarFile jar) throws IOException {
        Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()){
            JarEntry je = en.nextElement();
            String name = je.getName();
            if (name.endsWith(".class")){
                String clss = name.replace(".class", "").replaceAll("/", ".");
                if(this.findLoadedClass(clss) != null) continue;

                InputStream input = jar.getInputStream(je);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesNumRead = 0;
                while ((bytesNumRead = input.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesNumRead);
                }
                byte[] cc = baos.toByteArray();
                input.close();
                classByteMap.put(clss, cc);//暂时保存下来
            }
        }
    }

    public static void writeBytesToFile(String filename, byte[] bytes) throws IOException {
        OutputStream out = new FileOutputStream(filename);
        InputStream is = new ByteArrayInputStream(bytes);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }
}
