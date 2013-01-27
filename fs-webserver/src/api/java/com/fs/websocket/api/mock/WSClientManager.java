/**
 *  Jan 24, 2013
 */
package com.fs.websocket.api.mock;

import java.net.URI;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public abstract class WSClientManager<T extends WSClientWrapper> {
	public abstract void init(URI uri, Class<? extends T> wcls, ContainerI c);

	public abstract int size();

	public abstract T getFirstClient();

	public abstract T getLastClient();

	public abstract T getRandomClient();

	public abstract void removeRandomClient(boolean close);

	public abstract T createClient(boolean connect);

	public abstract T createClient(boolean connect, PropertiesI<Object> pts);

	public abstract void removeClient(boolean close);

	public abstract void removeClient(T client, boolean close);

	public abstract int total();

	public abstract List<T> getClientList();

	public static <T extends WSClientWrapper> WSClientManager<T> newInstance(URI uri,
			Class<? extends T> wcls, ContainerI c) {
		WSClientManager<T> rt = (WSClientManager<T>) ClassUtil
				.newInstance("com.fs.websocket.impl.mock.WSClientManagerImpl");
		rt.init(uri, wcls, c);
		return rt;
	}
}
