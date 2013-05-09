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
public interface BClientFactoryI<T extends BClient> {

	public static interface ProtocolI {

		public String getName();

		public Class<? extends AClientI> getClientClass();
	}

	public static class Builder {
		public static <T extends BClient> BClientFactoryI<T> newInstance(Class<? extends T> wcls, ContainerI c) {
			BClientFactoryI<T> rt = (BClientFactoryI<T>) ClassUtil
					.newInstance("com.fs.commons.impl.client.BClientManagerImpl");
			rt.init(wcls, c);
			return rt;
		}
	}

	public void init(Class<? extends T> wcls, ContainerI c);

	public void registerProtocol(ProtocolI pro, URI uri);

	// public void registerProtocol(String pro, Class<? extends AClientI> cls,
	// URI uri);

	public int size();

	public T getFirstClient();

	public T getLastClient();

	public T getRandomClient();

	public void removeRandomClient(boolean close);

	public T createClient(String prot, boolean connect);

	public T createClient(String prot, boolean connect, PropertiesI<Object> pts);

	public void removeClient(boolean close);

	public void removeClient(T client, boolean close);

	public int total();

	public List<T> getClientList();

}
