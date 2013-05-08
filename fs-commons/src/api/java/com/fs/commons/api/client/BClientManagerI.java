/**
 *  
 */
package com.fs.commons.api.client;

import java.net.URI;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public interface BClientManagerI<T extends BClient> {

	public static class Factory {
		public static <T extends BClient> BClientManagerI<T> newInstance(Class<? extends AClientI> cls, URI uri,
				Class<? extends T> wcls, ContainerI c) {
			BClientManagerI<T> rt = (BClientManagerI<T>) ClassUtil
					.newInstance("com.fs.commons.impl.client.BClientManagerImpl");
			rt.init(cls, uri, wcls, c);
			return rt;
		}
	}

	public void init(Class<? extends AClientI> cls, URI uri, Class<? extends T> wcls, ContainerI c);

	public int size();

	public T getFirstClient();

	public T getLastClient();

	public T getRandomClient();

	public void removeRandomClient(boolean close);

	public T createClient(boolean connect);

	public T createClient(boolean connect, PropertiesI<Object> pts);

	public void removeClient(boolean close);

	public void removeClient(T client, boolean close);

	public int total();

	public List<T> getClientList();

}
