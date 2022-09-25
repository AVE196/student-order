package edu.studentorder.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

public class SamplerClass {
	
	public static void main(String...args) {
		
		SamplerClass sc = new SamplerClass();
		sc.testMethod();
		
	}

	private void testMethod() {
		try {
		Class cl = Class.forName("edu.studentorder.dao.DirectoryDaoImplTest");
		Constructor ct = cl.getConstructor();
		Object ob = ct.newInstance();
		
		Method[] met = cl.getMethods();
		for (Method m: met) {
			Test at = m.getAnnotation(Test.class);
			if (at != null) m.invoke(ob);
		}
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
