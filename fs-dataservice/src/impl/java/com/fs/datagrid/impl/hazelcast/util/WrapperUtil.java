/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.impl.hazelcast.util;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.datagrid.api.DataWrapperI;

/**
 * @author wuzhen
 * 
 */
public class WrapperUtil {

	public static <V, W extends DataWrapperI> W wrapper(V v, Class<W> cls) {
		if (v == null) {
			return null;
		}
		W rt = ClassUtil.newInstance(cls);
		rt.setTarget(v);
		return rt;

	}
}
