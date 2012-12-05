/**
 * Jun 15, 2012
 */
package com.fs.commons.api.lang;

/**
 * @author wuzhen
 * 
 */
public class ClassUtil {
	public static <T> T newInstance(Class<T> cls) {
		return newInstance(cls, new Class<?>[] {}, new Object[] {});
	}

	public static Object newInstance(String cName) {
		return newInstance(cName, new Class<?>[] {}, new Object[] {});
	}

	public static <T> T newInstance(String cName, Class<?>[] paramTypes,
			Object[] paramValues) {

		Class<T> c = forName(cName);
		return newInstance(c, paramTypes, paramValues);
	}

	public static <T> T newInstance(Class<T> cls, Class<?>[] paramTypes,
			Object[] paramValues) {
		T rt;
		try {
			rt = cls.getConstructor(paramTypes).newInstance(paramValues);

		} catch (Exception e) {
			throw new FsException("[cannot instance:class:" + cls
					+ ",may not public?]", e);
		}
		return rt;
	}

	public static <T> Class<T> forName(String cName) {

		try {
			return (Class<T>) Class.forName(cName);
		} catch (ClassNotFoundException e) {

			throw FsException.toRuntimeException(e);

		}

	}
}
