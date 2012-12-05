/**
 * Jun 16, 2012
 */
package com.fs.commons.api.lang;

/**
 * @author wu
 * 
 */
public class ObjectUtil {

	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == null) {
			if (o2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return o1.equals(o2);
		}
	}

	public static boolean equals(Object[] os1, Object[] os2) {
		if (os1 == null) {
			if (os2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (os2 == null) {
				return false;
			} else {
				if (os1.length != os2.length) {
					return false;
				} else {
					for (int i = 0; i < os1.length; i++) {
						if (!nullSafeEquals(os1[i], os2[i])) {
							return false;
						}
					}
					return true;
				}
			}
		}
	}
}
