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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;
import com.fs.webserver.impl.test.mock.MockWSC;
import com.fs.webserver.impl.test.mock.MockWSCFactory;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;

/**
 * @author wu
 * 
 */
public class WebSocketBenchmark {
	private SPIManagerI sm;
	private ContainerI container;

	private MockWsServer server;

	private MockWSCFactory cf;

	private int concurrent;

	private int messages;

	private URI uri;

	private List<MockWSC> clientList;

	public WebSocketBenchmark(int cc, int mc) {
		this.concurrent = cc;
		this.messages = mc;
		this.clientList = new ArrayList<MockWSC>();
	}

	public static void main(String[] args) {
		new WebSocketBenchmark(11, 1).start();
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

		name = "clientFactory";
		tm.start(name);
		this.startClientFactory();
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
			MockWSC client = cf.newClient("client-" + i, uri);
			this.clientList.add(client);
		}
		this.inExecutorForEachClient(new CallbackI<MockWSC, Object>() {

			@Override
			public Object execute(MockWSC ci) {
				Future<MockWSC> fc = ci.connect();//
				MockWSC c;
				try {
					c = fc.get(1000, TimeUnit.MILLISECONDS);
					fc = ci.session();
					c = fc.get(1000, TimeUnit.MILLISECONDS);
				} catch (Exception e) {
					throw FsException.toRtE(e);
				}
				// sessionID
				return null;
			}
		});

	}

	public void closeClients() {

		this.inExecutorForEachClient(new CallbackI<MockWSC, Object>() {

			@Override
			public Object execute(MockWSC ci) {
				ci.close();
				return null;
			}
		});

	}

	private void inExecutorForEachClient(final CallbackI<MockWSC, Object> cb) {
		this.inExecutor(this.clientList.size(), new CallbackI<Integer, Object>() {

			@Override
			public Object execute(Integer i) {
				MockWSC client = WebSocketBenchmark.this.clientList.get(i);
				return cb.execute(client);
			}

		});
	}

	private void inExecutor(int loop, final CallbackI<Integer, Object> cb) {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < loop; i++) {
			final int fI = i;
			executor.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return cb.execute(fI);
				}
			});

		}
		executor.shutdown();
		int timeout = 1000;
		try {
			executor.awaitTermination(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
	}

	public void startClientFactory() {
		cf = new MockWSCFactory();
		cf.start();
	}

	public void startServer() {
		this.server = new MockWsServer("testws", this.container);
		this.server.start();

	}

	public void init() {
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
	}

	public void shutdown() {
		this.sm.shutdown();
	}
}
