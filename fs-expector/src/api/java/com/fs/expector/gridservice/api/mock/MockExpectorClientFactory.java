/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.api.mock;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wuzhen
 * 
 */
public abstract class MockExpectorClientFactory {

	private static final String CNAME = "com.fs.expector.gridservice.impl.mock.MockExpectorClientFactoryImpl";

	public static MockExpectorClientFactory getInstance(ContainerI c) {
		MockExpectorClientFactory rt = (MockExpectorClientFactory) ClassUtil
				.newInstance(CNAME);
		rt.start(c);
		return rt;
	}

	public abstract MockExpectorClientFactory start(ContainerI c);

	public MockExpectorClient newClient() {
		String url = "ws://localhost:8080/wsa/default";
		return newClient(url);
	}

	public abstract MockExpectorClient newClient(String url);
}
