/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;
import com.fs.webserver.impl.test.mock.MockWSClientWrapper;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;
import com.fs.websocket.api.mock.WSClientManager;

/**
 * @author wu
 *         <p>
 *         This benchmark collect the basic performance metrics that depend on
 *         the concurrent clients. Open them(include send message), and Close
 *         them.
 */
public class WebSocketBenchmark {

	private SPIManagerI sm;

	private ContainerI container;

	private MockWsServer server;

	private int concurrent;

	private int messages;

	private URI uri;

	private boolean sendReadyMessageAtConnection;

	private WSClientManager<MockWSClientWrapper> manager;

	public WebSocketBenchmark(int cc, int mc) {
		this.concurrent = cc;
		this.messages = mc;
		this.sendReadyMessageAtConnection = false;
	}

	public static void main(String[] args) {
		// TODO cmd line argument
		new WebSocketBenchmark(100, 1).start();
	}

	public void start() {
		TimeMeasures tm = new TimeMeasures();
		String name = "init";
		tm.start(name);
		this.init();
		tm.end(name);

		name = "startServer";
		tm.start(name);
		this.startServer();
		tm.end(name);

		name = "startClients";
		tm.start(name);
		this.startClients();
		tm.end(name, this.concurrent);

		name = "closeClients";
		tm.start(name);
		this.closeClients();
		tm.end(name, this.concurrent);

		name = "shutdown";
		tm.start(name);
		this.shutdown();
		tm.end(name);

		tm.print();
		System.exit(0);
	}

	public void startClients() {
		try {
			this.uri = new URI("ws://localhost:8080/wsa/testws");
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}

		for (int i = 0; i < this.concurrent; i++) {

			MockWSClientWrapper client = this.manager.createClient(false);
		}
		this.inExecutorForEachClient(new CallbackI<MockWSClientWrapper, Object>() {

			@Override
			public Object execute(MockWSClientWrapper ci) {
				ci.connect();//

				ci.createSession();

				// sessionID
				return null;
			}
		});

	}

	public void closeClients() {

		this.inExecutorForEachClient(new CallbackI<MockWSClientWrapper, Object>() {

			@Override
			public Object execute(MockWSClientWrapper ci) {
				ci.close();
				return null;
			}
		});

	}

	private void inExecutorForEachClient(final CallbackI<MockWSClientWrapper, Object> cb) {
		final List<MockWSClientWrapper> cl = this.manager.getClientList();
		this.inExecutor(cl.size(), new CallbackI<Integer, Object>() {

			@Override
			public Object execute(Integer i) {
				MockWSClientWrapper client = cl.get(i);
				return cb.execute(client);
			}

		});
	}

	private void inExecutor(int loop, final CallbackI<Integer, Object> cb) {
		ExecutorService executor = Executors.newCachedThreadPool();

		List<Future<Object>> fL = new ArrayList<Future<Object>>();

		for (int i = 0; i < loop; i++) {
			final int fI = i;
			Future<Object> f = executor.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {

					return cb.execute(fI);

				}
			});
			fL.add(f);

		}
		executor.shutdown();
		int timeout = 1000;
		try {
			executor.awaitTermination(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		for (Future<Object> f : fL) {
			try {
				f.get(10, TimeUnit.SECONDS);
			} catch (Exception e) {
				throw FsException.toRtE(e);
			}
		}
	}

	public void startServer() {
		this.server = new MockWsServer("testws", this.container, this.sendReadyMessageAtConnection);
		this.server.start();

	}

	public void init() {
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.manager = WSClientManager.newInstance(MockWSClientWrapper.class, this.container);
	}

	public void shutdown() {
		this.sm.shutdown();
	}
}
