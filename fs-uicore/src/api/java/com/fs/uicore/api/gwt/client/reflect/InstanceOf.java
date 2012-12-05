/**
 * Jun 25, 2012
 */
package com.fs.uicore.api.gwt.client.reflect;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wuzhen
 * 
 */
public class InstanceOf {

	public static interface CheckerI {

		public Class getCheckClass();

		public boolean isInstance(Object o);

	}

	public static abstract class CheckerSupport implements InstanceOf.CheckerI {

		protected Class checkClass;

		public CheckerSupport(Class cc) {
			this.checkClass = cc;
		}

		/*
		
		 */
		@Override
		public Class getCheckClass() {
			return this.checkClass;
		}

	}

	private static Map<Class, CheckerI> map = new HashMap<Class, CheckerI>();

	public static void addChecker(CheckerI ck) {
		map.put(ck.getCheckClass(), ck);
	}

	public static boolean isInstance(Class cls, Object o) {
		CheckerI ck = map.get(cls);
		if (ck == null) {
			throw new UiException("no instanceof checker found:" + cls
					+ ",please add it in the your spi's active() method");
		}
		return ck.isInstance(o);
	}

}
