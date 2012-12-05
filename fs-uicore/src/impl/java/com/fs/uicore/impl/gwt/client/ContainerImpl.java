/**
 * Jul 1, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.util.CollectionUtil;

/**
 * @author wu
 * 
 */
public class ContainerImpl implements ContainerI {

	protected List<Object> objects = new ArrayList<Object>();

	/* */
	@Override
	public <T> List<T> getList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (Object o : objects) {
			if (InstanceOf.isInstance(cls, o)) {
				rt.add((T) o);
			}
		}
		return rt;

	}

	/* */
	@Override
	public <T> T get(Class<T> cls, boolean force) {

		return CollectionUtil.single(this.getList(cls), force);

	}

	/* */
	@Override
	public void add(Object obj) {
		this.objects.add(obj);
		if (obj instanceof ContainerI.AwareI) {
			((ContainerI.AwareI) obj).setContainer(this);
		}
	}

}
