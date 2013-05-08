/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webserver.impl.test.mock.MockWsBClient;

/**
 * @author wu
 *         <p>
 *         This benchmark open certain clients,send random messages and close
 *         them.
 */
public class WebSocketBenchmark3 extends WebSocketWSClientRunner {

	private static Logger LOG = LoggerFactory.getLogger(WebSocketBenchmark3.class);

	public WebSocketBenchmark3(int initClients, int maxCon, int maxEffort) {
		super(initClients, maxCon, maxEffort, -1);
	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {
		super.init();

	}

	@Override
	protected MockWsBClient createClient(int idx) {
		return super.createClient(idx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.mock.WSClientRunner#work(int)
	 */
	@Override
	protected void work(int idx) {
		MockWsBClient client = this.clients.getRandomClient();
		String text = "text-for-echo-" + idx;
		client.echo(text);

	}/*
	 * Jan 27, 2013
	 */

}
