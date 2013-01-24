/**
 *  Jan 23, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;

/**
 * @author wuzhen
 * 
 */
public class TerminalBenchmark2 {
	private static Logger LOG = LoggerFactory.getLogger(TerminalBenchmark2.class);

	private SPIManagerI sm;
	private ContainerI container;

	private int concurrent;

	private int time;// ms

	private WsClientsKeeper clients;

	private int workRate;

	private int max;

	public TerminalBenchmark2(int cc, int max, int rate, int duration) {
		this.concurrent = cc;
		this.max = max;
		this.workRate = rate;
		this.time = duration;

	}

	public static void main(String[] args) {
		// TODO cmd line argument
		new TerminalBenchmark2(10, 100000, 10, 1 * 60 * 1000).start();
	}

	public void start() {
		TimeMeasures tm = new TimeMeasures();
		String name = "init";
		tm.start(name);
		this.init();
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

		Runnable cmd = new Runnable() {

			@Override
			public void run() {
				try {
					TerminalBenchmark2.this.tryOpenOrCloseClient();
				} catch (Throwable t) {
					TerminalBenchmark2.LOG.error("", t);
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

	public void init() {
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.clients = new WsClientsKeeper(this.container);
	}

	public void shutdown() {
		this.sm.shutdown();
	}
}