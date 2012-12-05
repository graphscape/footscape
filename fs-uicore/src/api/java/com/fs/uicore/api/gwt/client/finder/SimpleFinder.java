/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uicore.api.gwt.client.finder;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.support.UiObjectFinderSupport;

/**
 * @author wu
 * 
 */
public class SimpleFinder<T> extends UiObjectFinderSupport<T> {

	protected Class<? extends T> clazz;

	protected Path path;

	@Override
	public List<T> findList() {
		List<T> rt = new ArrayList<T>();

		this.find(this.startPoint, rt);

		return rt;
	}

	protected void find(UiObjectI obj, List<T> result) {
		if (this.isMatch(obj)) {
			result.add((T) obj);
		}
		List<UiObjectI> ol = obj.getChildList(UiObjectI.class);
		for (UiObjectI c : ol) {
			find(c, result);
		}
	}

	protected boolean isMatch(UiObjectI obj) {
		if (this.clazz != null && !InstanceOf.isInstance(this.clazz, obj)) {
			return false;
		}
		if (this.path != null && !obj.getPath().equals(this.path)) {
			return false;
		}

		return true;
	}

}
