/**
 * Jul 1, 2012
 */
package com.fs.uicore.api.gwt.client;

import java.util.List;

/**
 * @author wu
 * 
 */
public interface ContainerI {

	public static interface AwareI {
		public void init(ContainerI c);
	}

	public <T> List<T> getList(Class<T> cls);

	public <T> T get(Class<T> cls, boolean force);

	public void add(Object obj);

	public EventBusI getEventBus();

}
