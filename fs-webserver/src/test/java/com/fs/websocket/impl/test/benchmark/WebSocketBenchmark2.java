/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;
import com.fs.websocket.api.mock.AbstractWSClientManager;
import com.fs.websocket.api.mock.WSClient;

/**
 * @author wu
 *         <p>
 *         This benchmark go even far by continuslly(for certain time span) open
 *         client,send random messages and close them.
 */
public class WebSocketBenchmark2 {
	private static Logger LOG = LoggerFactory.getLogger(WebSocketBenchmark2.class);

	private SPIManagerI sm;
	private ContainerI container;

	private MockWsServer server;

	private int concurrent;

	private int time;// ms

	private AbstractWSClientManager<WSClient> clients;

	private int workRate;

	private int max;

	private int maxErrors = 1;

	public WebSocketBenchmark2(int cc, int max, int rate, int duration) {
		this.concurrent = cc;
		this.max = max;
		this.workRate = rate;
		this.time = duration;
		this.clients = new WSClientManagerImpl();
	}

	public static void main(String[] args) {
		// TODO cmd line argument
		new WebSocketBenchmark2(100, 100000, 10, 100 * 3600 * 1000).start();
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

		name = "doWork";
		tm.start(name);
		this.doWork();
		tm.end(name);

		name = "closeRemains";
		tm.start(name);
		this.closeRemains();
		tm.end(name);

		name = "shutdown";
		tm.start(name);
		this.shutdown();
		tm.end(name);

		tm.print();
		System.exit(0);
	}

	private void doWork() {
		LOG.info("doWork...");

		final long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newCachedThreadPool();
		final AtomicInteger errs = new AtomicInteger();

		Runnable cmd = new Runnable() {

			@Override
			public void run() {
				try {
					WebSocketBenchmark2.this.tryOpenOrCloseClient();
				} catch (Throwable t) {
					errs.incrementAndGet();
					WebSocketBenchmark2.LOG.error("", t);
				}
			}
		};

		// wait timeout
		while (true) {

			executor.submit(cmd);

			long end = System.currentTimeMillis();

			int remain = this.time - (int) (end - start);

			if (remain <= 0) {
				break;
			}
			if (this.clients.total() > this.max) {
				break;
			}
			if (errs.get() >= this.maxErrors) {
				LOG.warn("break for too many error:" + errs.get());
				break;
			}

			// LOG.info("waiting timeout:" + remain);

			try {
				Thread.sleep(this.workRate);
			} catch (InterruptedException e) {

			}
		}

		executor.shutdown();
		int timeout = 1000;
		try {
			executor.awaitTermination(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

		LOG.info("done of doWork");

	}

	private void tryOpenOrCloseClient() {

		LOG.debug("tryOpenOrCloseClient,size:" + this.clients.size());
		if (this.concurrent > this.clients.size()) {
			this.clients.createClient(true);
		} else {
			this.clients.removeClient(true);
		}

	}

	private void closeRemains() {
		LOG.debug("closeRemains,size:" + this.clients.size());
		while (this.clients.size() > 0) {
			this.clients.removeClient(true);
		}
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
