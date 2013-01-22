/**
 *  Jan 22, 2013
 */
package com.fs.expector.gridservice.impl.test.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.api.mock.MockExpectorClientFactory;

/**
 * @author wuzhen
 * 
 */
public class ExpSearchBenchmark {
	static class Metric {

		public String name;
		public long amount = 1;
		public long start;
		public long end;

		public long avg() {

			return this.total() / this.amount;
		}

		public long total() {
			return this.end - this.start;
		}

		/**
		 * Jan 22, 2013
		 */
		public void end() {
			this.end = System.currentTimeMillis();
		}

		public String toString() {
			return "metric," + ",\tavg:" + this.avg() + ",\tname:" + name + ",\tstart:" + start + ",\tend:"
					+ end + ",\tamount:" + amount + ",\ttotal:" + this.total();
		}
	}

	static class Metrics {
		Map<String, Metric> metricMap = new HashMap<String, Metric>();

		public void start(String name) {
			get(name).start = System.currentTimeMillis();
		}

		public synchronized Metric get(String name) {

			Metric m = this.metricMap.get(name);
			if (m == null) {
				m = new Metric();
				m.name = name;
				this.metricMap.put(name, m);
			}

			return m;
		}

		public void end(String name, int ammount) {

			Metric m = get(name);
			m.amount = ammount;
			m.end();
		}

		public void end(String name) {
			this.get(name).end();
		}

		public void print() {
			for (Metric m : this.metricMap.values()) {
				System.out.println(m);
			}
		}
	}

	protected SPIManagerI sm;

	protected ContainerI container;

	protected DataServiceI dataService;

	protected MessageServiceI engine;

	protected MockExpectorClientFactory cfactory;

	protected int clientCount;

	protected int expCountForEachUser;

	protected List<MockExpectorClient> clientList;

	protected Metrics metrics;

	public ExpSearchBenchmark(int cc, int ec) {
		this.clientCount = cc;
		this.expCountForEachUser = ec;
		this.metrics = new Metrics();
	}

	public static void main(String[] args) throws Exception {
		new ExpSearchBenchmark(100, 100).start();
	}

	public void start() {
		String metric = "init";
		this.metrics.start(metric);
		this.init();
		this.metrics.end(metric);

		metric = "start-clients";
		this.metrics.start(metric);
		this.startClients();
		this.metrics.end(metric, this.clientCount);

		metric = "exp-create";
		this.metrics.start(metric);
		this.populateExpData();
		this.metrics.end(metric, this.clientCount * this.expCountForEachUser);

		metric = "search";
		this.metrics.start(metric);
		this.searchExpData();
		this.metrics.end(metric, this.clientCount);
		this.sm.shutdown();
		this.metrics.print();
		// TODO remove this code:
		System.exit(0);//
	}

	private void init() {

		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

		this.cfactory = MockExpectorClientFactory.getInstance(this.container);//

		this.clientList = new ArrayList<MockExpectorClient>();
	}

	private void populateExpData() {
		this.inExecutorForEachClient(new CallbackI<MockExpectorClient, Object>() {

			@Override
			public Object execute(MockExpectorClient client) {
				ExpSearchBenchmark.this.populateExpForUser(client);//
				return null;

			}
		});
	}

	private void populateExpForUser(MockExpectorClient client) {
		String accId = client.getAccountId();
		for (int i = 0; i < this.expCountForEachUser; i++) {
			String body1 = accId + " is exepecting number" + i + ",what is yours,";
			client.newExp(body1);
		}

	}

	private void inExecutorForEachClient(final CallbackI<MockExpectorClient, Object> cb) {
		this.inExecutor(this.clientList.size(), new CallbackI<Integer, Object>() {

			@Override
			public Object execute(Integer i) {
				MockExpectorClient client = ExpSearchBenchmark.this.clientList.get(i);
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

	private void searchForUser(MockExpectorClient user) {
		String phrase = "expecting number1";
		user.search(false, 0, 10, null, phrase, 3);
	}

	private void searchExpData() {

		this.inExecutorForEachClient(new CallbackI<MockExpectorClient, Object>() {

			@Override
			public Object execute(MockExpectorClient client) {
				ExpSearchBenchmark.this.searchForUser(client);//
				return null;

			}
		});

	}

	private void startClients() {

		for (int i = 0; i < this.clientCount; i++) {
			MockExpectorClient client = ExpSearchBenchmark.this.cfactory.newClient();
			this.clientList.add(client);
		}

		this.inExecutor(this.clientCount, new CallbackI<Integer, Object>() {

			@Override
			public Object execute(Integer i) {
				String user = "user" + i;
				String email = user + "@domin.com";
				String nick = user;
				MockExpectorClient client = ExpSearchBenchmark.this.clientList.get(i);
				client.start(email, nick, nick);
				return client;
			}
		});

	}

}
