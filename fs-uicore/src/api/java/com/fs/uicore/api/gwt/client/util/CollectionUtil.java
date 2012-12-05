/**
 * Jul 1, 2012
 */
package com.fs.uicore.api.gwt.client.util;

import java.util.Collection;

import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 * 
 */
public class CollectionUtil {

	public static <T> T single(Collection<T> cs, boolean force) {
		if (cs.isEmpty()) {
			if (force) {
				throw new UiException("force:");
			} else {
				return null;
			}
		} else if (cs.size() > 1) {
			throw new UiException("duplicated:" + cs);
		} else {
			return cs.iterator().next();
		}
	}
}
