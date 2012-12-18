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

	public static <V, W extends WrapperGdI> W wrapper(V v, Class<W> cls) {
		if (v == null) {
			return null;
		}
		W rt = ClassUtil.newInstance(cls);
		rt.setTarget(v);
		return rt;

	}
}
