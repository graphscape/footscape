/**
 *  Jan 23, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.net.URI;

import com.fs.commons.api.client.BClientFactoryI;
import com.fs.gridservice.commons.api.mock.MockClientWrapper;
import com.fs.websocket.api.mock.WSClientRunner;

/**
 * @author wuzhen
 * 
 */
public class TerminalBenchmark2 extends WSClientRunner<MockClientWrapper> {

	/**
	 * @param uri
	 * @param wcls
	 * @param cc
	 * @param max
	 * @param rate
	 * @param duration
	 */
	public TerminalBenchmark2(BClientFactoryI.ProtocolI pro, URI uri, int cc, int max, int duration) {
		this(pro, uri, MockClientWrapper.class, cc, max, duration);

	}

	public TerminalBenchmark2(BClientFactoryI.ProtocolI pro, URI uri,
			Class<? extends MockClientWrapper> wcls, int cc, int max, int duration) {
		super(pro, uri, wcls, 0, cc, max, duration);
	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {
		super.init();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.mock.WSClientRunner#work(int)
	 */
	@Override
	protected void work(int idx) {
		// TODO Auto-generated method stub

	}

}
