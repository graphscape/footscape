/**
 *  Jan 24, 2013
 */
package com.fs.websocket.api.mock;

import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wuzhen
 * 
 */
public abstract class WSClientManager<T extends WsClientWrapper> {
	public abstract void init(Class<T> wcls, ContainerI c);

	public abstract int size();

	public abstract T getFirstClient();

	public abstract T getLastClient();

	public abstract T getRandomClient();

	public abstract void removeRandomClient(boolean close);

	public abstract T createClient(boolean connect);

	public abstract void removeClient(boolean close);

	public abstract void removeClient(T client, boolean close);

	public abstract int total();
	
	public abstract List<T> getClientList();

	public static <T extends WsClientWrapper> WSClientManager<T> newInstance(Class<T> wcls, ContainerI c) {
		WSClientManager<T> rt = (WSClientManager<T>) ClassUtil
				.newInstance("com.fs.websocket.impl.mock.WSClientManagerImpl");
		rt.init(wcls, c);
		return rt;
	}
}
