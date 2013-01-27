/**
 *  Jan 23, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.net.URI;

import com.fs.gridservice.commons.api.mock.MockClientWrapper;
import com.fs.gridservice.commons.impl.test.GsCommonsTestSPI;
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
	public TerminalBenchmark2(int cc, int max, int duration) {
		this(GsCommonsTestSPI.DEFAULT_WS_URI, MockClientWrapper.class, cc, max, duration);

	}

	public TerminalBenchmark2(URI uri, Class<? extends MockClientWrapper> wcls, int cc, int max, int duration) {
		super(uri, wcls, cc, max, duration);
	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {
		super.init();

	}

}
