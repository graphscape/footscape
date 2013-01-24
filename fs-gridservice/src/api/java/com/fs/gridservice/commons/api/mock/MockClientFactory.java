/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.api.mock;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wuzhen
 * 
 */
public abstract class MockClientFactory {

	private static final String CNAME = "com.fs.gridservice.commons.impl.mock.MockClientFactoryImpl";

	public static MockClientFactory getInstance(ContainerI c) {
		MockClientFactory rt = (MockClientFactory) ClassUtil.newInstance(CNAME);
		rt.start(c);
		return rt;
	}

	public abstract MockClientFactory start(ContainerI c);

	public MockClient newClient(String name) {
		String url = "ws://localhost:8080/wsa/default";
		return newClient(name, url);
	}

	protected abstract MockClient newClient(String name, String url);

}
