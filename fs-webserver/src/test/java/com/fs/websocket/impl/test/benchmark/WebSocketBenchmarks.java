/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import com.fs.websocket.api.Components;
import com.fs.websocket.impl.test.WebSocketTestSPI;

/**
 * @author wu
 *         <p>
 *         This benchmark collect the basic performance metrics that depend on
 *         the concurrent clients. Open them(include send message), and Close
 *         them.
 */
public class WebSocketBenchmarks {

	public static void main(String[] args) {
		new WebSocketBenchmark3(Components.WEBSOCKET, WebSocketTestSPI.TEST_WS_URI, 100, 30, 10000).start();
	}

}
