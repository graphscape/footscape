/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 *         <p>
 *         This benchmark go even far by continuslly(for certain time span) open
 *         client,send random messages and close them.
 */
public class WebSocketBenchmark2 extends CustomWSClientRunner {
	private static Logger LOG = LoggerFactory.getLogger(WebSocketBenchmark2.class);

	public WebSocketBenchmark2(int cc, int max, int duration) {
		super(cc, max, duration);
	}

	public static void main(String[] args) {
		// TODO cmd line argument
		new WebSocketBenchmark2(100, -1, 10*1000).start();
	}

	/*
	 *Jan 27, 2013
	 */
	@Override
	public void init() {
		super.init();
	}

	/*
	 *Jan 27, 2013
	 */
	
	
	

}
