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
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.benchmark.TimeMeasures;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.MockClientFactory;

/**
 * @author wuzhen
 * 
 */
public class TerminalBenchmark {

	protected SPIManagerI sm;
	protected ContainerI container;

	protected MockClientFactory factory;

	private List<MockClient> clientList;

	protected int clients;
	protected int messages;

	public TerminalBenchmark(int clients, int messages) {
		this.clients = clients;
		this.messages = messages;
		this.clientList = new ArrayList<MockClient>();
	}

	public static void main(String[] args) throws Exception {
		new TerminalBenchmark(300, 10).start();
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
		tm.end(metric,this.clients);
		
		metric = "closeClients";
		tm.start(metric);
		this.closeClients();
		tm.end(metric,this.clients);
		
		this.sm.shutdown();
		
		tm.print();
		
		System.exit(0);//TODO remove
	}

	public void init() {
		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.factory = MockClientFactory.getInstance(this.container);
	}

	public void startClients() {
		for (int i = 0; i < this.clients; i++) {
			MockClient ci = this.factory.newClient();
			this.clientList.add(ci);
		}
		this.inExecutorForEachClient(new CallbackI<MockClient, Object>() {

			@Override
			public Object execute(MockClient ci) {
				ci.connect();
				return null;
			}
		});
	}

	public void closeClients() {

		this.inExecutorForEachClient(new CallbackI<MockClient, Object>() {

			@Override
			public Object execute(MockClient ci) {
				ci.close();
				return null;
			}
		});
	}

	private void inExecutorForEachClient(final CallbackI<MockClient, Object> cb) {
		this.inExecutor(this.clientList.size(), new CallbackI<Integer, Object>() {

			@Override
			public Object execute(Integer i) {
				MockClient client = TerminalBenchmark.this.clientList.get(i);
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
