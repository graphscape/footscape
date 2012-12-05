/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package com.fs.dataservice.api.core.util;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class NodeWrapperUtil {
	public static <W extends NodeWrapper> List<PropertiesI<Object>> convert(
			List<W> wl, String[] from, boolean[] force, String[] to) {
		List<PropertiesI<Object>> rt = new ArrayList<PropertiesI<Object>>();
		for (W w : wl) {
			PropertiesI<Object> pts = w.convert(from, force, to);
			rt.add(pts);
		}
		return rt;
	}

}
