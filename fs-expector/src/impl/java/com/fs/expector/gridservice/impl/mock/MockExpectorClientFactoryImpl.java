/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.mock;

import com.fs.commons.api.ContainerI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.api.mock.MockExpectorClientFactory;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.MockClientFactory;

/**
 * @author wuzhen
 * 
 */
public class MockExpectorClientFactoryImpl extends MockExpectorClientFactory {

	protected MockClientFactory factory;

	protected ContainerI container;

	public MockExpectorClientFactoryImpl() {
	}

	@Override
	public MockExpectorClientFactory start(ContainerI c) {
		MockClientFactory f = MockClientFactory.getInstance(c);
		MockExpectorClientFactoryImpl rt = new MockExpectorClientFactoryImpl();
		rt.factory = f;
		rt.container = c;
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.expector.gridservice.api.mock.MockExpectorClientFactory#newClient
	 * (java.lang.String)
	 */
	@Override
	public MockExpectorClient newClient(String url) {
		MockClient mc = this.factory.newClient(url);

		return new MockExpectorClientImpl(mc, this.container);

	}
}
