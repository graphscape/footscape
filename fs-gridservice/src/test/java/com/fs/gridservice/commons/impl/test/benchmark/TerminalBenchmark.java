/**
 *  Jan 23, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

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
import com.fs.commons.api.client.BClientFactoryI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;
import com.fs.gridservice.commons.api.mock.MockClientWrapper;

/**
 * @author wuzhen
 * 
 */
public class TerminalBenchmark {

	protected SPIManagerI sm;
	protected ContainerI container;

	protected BClientFactoryI<MockClientWrapper> factory;

	private List<MockClientWrapper> clientList;

	protected int clients;
	protected int messages;

	protected BClientFactoryI.ProtocolI protocol;

	public TerminalBenchmark(BClientFactoryI.ProtocolI protocol, int clients, int messages) {
		this.protocol = protocol;
		this.clients = clients;
		this.messages = messages;
		this.clientList = new ArrayList<MockClientWrapper>();

	}

	public static void main(String[] args) throws Exception {
		new TerminalBenchmark(null, 100, 10).start();
	}

	public void start() {
		TimeMeasures tm = new TimeMeasures();
		String metric = "init";
		tm.start(metric);
		this.init();
		tm.end(metric);

		metric = "startClients";
		tm.start(metric);
		this.startClients();
		tm.end(metric, this.clients);

		metric = "closeClients";
		tm.start(metric);
		this.closeClients();
		tm.end(metric, this.clients);

		this.sm.shutdown();

		tm.print();

		System.exit(0);// TODO remove
	}

	public void init() {
		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.factory = BClientFactoryI.Builder.newInstance(MockClientWrapper.class, this.container);

	}

	public void startClients() {
		for (int i = 0; i < this.clients; i++) {
			MockClientWrapper ci = this.factory.createClient(this.protocol.getName(), false);

			this.clientList.add(ci);
		}
		this.inExecutorForEachClient(new CallbackI<MockClientWrapper, Object>() {

			@Override
			public Object execute(MockClientWrapper ci) {
				ci.connect();
				return null;
			}
		});
	}

	public void closeClients() {

		this.inExecutorForEachClient(new CallbackI<MockClientWrapper, Object>() {

			@Override
			public Object execute(MockClientWrapper ci) {
				ci.close();
				return null;
			}
		});
	}

	private void inExecutorForEachClient(final CallbackI<MockClientWrapper, Object> cb) {
		this.inExecutor(this.clientList.size(), new CallbackI<Integer, Object>() {

			@Override
			public Object execute(Integer i) {
				MockClientWrapper client = TerminalBenchmark.this.clientList.get(i);
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

}
