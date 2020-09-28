package ys.payloads.util;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URL;

public class CommonUtil {
    public static byte[] getFileBytes(String file) {
        try {
            File f = new File(file);
            int length = (int) f.length();
            byte[] data = new byte[length];
            new FileInputStream(f).read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getJarPath(String jarname){
        String url = null;
        try {
//            String path = "jar:xxx:rsrc:/"+jarname+"!/";
//            System.out.println(path);
//            return path;
//            String path = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + jarname).getAbsoluteFile().getPath();
//            if(path.contains(":")){
//                return path;
//            }else{
//                path = "file://" + path;
//            }
//            return path;
            String path = zipToTmp(jarname).getPath();
            if(path.contains("://")){
                url = path;
            }else{
                url = "file://" + path;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL zipToTmp(String jarname){
        jarname = jarname.replaceAll(".jar",".txt");
        URL url = null;
        try {
            final File tempFile = File.createTempFile("Temp", ".jar");
            tempFile.deleteOnExit();
            InputStream is = CommonUtil.class.getClassLoader().getResourceAsStream(jarname);
            String base64Str = CommonUtil.readStringFromInputStream(is);
            byte[] bb = new BASE64Decoder().decodeBuffer(base64Str);
            CommonUtil.writeBytesToFile(tempFile.getAbsolutePath(),bb);
            url = tempFile.toURI().toURL();
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    private static void copyFileUsingStream(InputStream is, File dest) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static byte[] readBytesFromInputStream(InputStream input) throws IOException {
        if (input == null) {
            return null;
        }
        byte[] bcache = new byte[2048];
        int readSize = 0;//每次读取的字节长度
        int totalSize = 0;//总字节长度
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        while ((readSize = input.read(bcache)) > 0) {
            totalSize += readSize;
            infoStream.write(bcache, 0, readSize);
        }
        input.close();
        return infoStream.toByteArray();
    }

    public static String readStringFromInputStream(InputStream input){
        try {
            if (input == null) {
                return null;
            }
            byte[] bcache = new byte[2048];
            int readSize = 0;//每次读取的字节长度
            int totalSize = 0;//总字节长度
            ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
            try {
                while ((readSize = input.read(bcache)) > 0) {
                    totalSize += readSize;
                    infoStream.write(bcache, 0, readSize);
                }
            } catch (IOException e) {
                throw e;
            } finally {
                input.close();
            }
            return infoStream.toString("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
