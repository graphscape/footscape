/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.impl.hazelcast.util;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.gridservice.core.api.WrapperGdI;

/**
 * @author wuzhen
 * 
 */
public class WrapperUtil {

	public static <V, W extends WrapperGdI<V>> W wrapper(V v, Class<W> cls) {
		if (v == null) {
			return null;
		}
		W rt = ClassUtil.newInstance(cls);
		rt.setTarget(v);
		return rt;

	}

	public static <V, W extends WrapperGdI<V>> V unwrap(W w, Class<W> cls) {
		if (w == null) {
			return null;
		}
		return w.getTarget();

	}
}
