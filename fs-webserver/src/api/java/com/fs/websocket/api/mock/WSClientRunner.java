/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 27, 2013
 */
package com.fs.websocket.api.mock;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;

/**
 * @author wu
 * 
 */
public class WSClientRunner<T extends WSClientWrapper> {
	protected static Logger LOG = LoggerFactory.getLogger(WSClientRunner.class);

	protected SPIManagerI sm;

	protected ContainerI container;

	protected int concurrent;

	protected int time;// ms

	protected Class<? extends T> wcls;

	protected URI uri;

	protected WSClientManager<T> clients;

	protected int max;

	protected AtomicInteger effort;

	protected int maxErrors = 1;

	protected Semaphore workers;// concurrent number of workers

	public WSClientRunner(URI uri, Class<? extends T> wcls, int cc, int max, int duration) {
		this.uri = uri;
		this.wcls = wcls;
		this.concurrent = cc;
		this.max = max;
		this.time = duration;
		this.workers = new Semaphore(this.concurrent);
		this.effort = new AtomicInteger();
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
		tm.end(name, this.effort.get());

		name = "closeRemains";
		int size = this.clients.size();
		tm.start(name);
		this.closeRemains();
		tm.end(name, size);

		name = "shutdown";
		tm.start(name);
		this.shutdown();
		tm.end(name);

		tm.print();
		System.exit(0);
	}

	protected void doWork() {
		LOG.info("doWork...");

		final long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newCachedThreadPool();
		final AtomicInteger errs = new AtomicInteger();

		Runnable cmd = new Runnable() {

			@Override
			public void run() {
				try {
					WSClientRunner.this.work();

				} catch (Throwable t) {
					errs.incrementAndGet();
					WSClientRunner.LOG.error("", t);
				} finally {
					WSClientRunner.this.effort.incrementAndGet();
					WSClientRunner.this.workers.release();
				}
			}
		};

		// wait timeout
		while (true) {

			try {
				boolean act = this.workers.tryAcquire(1, TimeUnit.SECONDS);
				if (!act) {
					continue;
				}
			} catch (InterruptedException e1) {
				continue;
			}
			executor.submit(cmd);

			if (this.time > 0) {

				long end = System.currentTimeMillis();

				int remain = this.time - (int) (end - start);
				if (remain < 0) {
					LOG.warn("break for time up:" + this.time);
					break;
				}
			}

			if (this.max > 0 && this.effort.get() > this.max) {
				LOG.warn("break caused by max effort(loop)" + this.effort.get());
				break;
			}
			if (errs.get() >= this.maxErrors) {
				LOG.warn("break caused by too many error:" + errs.get());
				break;
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

	protected void work() {

		LOG.debug("tryOpenOrCloseClient,size:" + this.clients.size());
		if (this.concurrent > this.clients.size()) {
			this.clients.createClient(true);
		} else {
			this.clients.removeClient(true);
		}

	}

	protected void closeRemains() {
		LOG.debug("closeRemains,size:" + this.clients.size());
		while (this.clients.size() > 0) {
			this.clients.removeClient(true);
		}
	}

	protected void init() {
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.clients = WSClientManager.newInstance(this.uri, this.wcls, this.container);
	}

	protected void shutdown() {
		this.sm.shutdown();
	}
}
