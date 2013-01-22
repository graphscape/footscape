/**
 *  Jan 22, 2013
 */
package com.fs.expector.gridservice.impl.test.benchmark;

import java.util.ArrayList;
import java.util.List;
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

	protected SPIManagerI sm;

	protected ContainerI container;

	protected DataServiceI dataService;

	protected MessageServiceI engine;

	protected MockExpectorClientFactory cfactory;

	protected int clientCount = 1;

	protected int expCountForEachUser = 1;

	protected List<MockExpectorClient> clientList;

	public ExpSearchBenchmark() {

	}

	public static void main(String[] args) throws Exception {
		new ExpSearchBenchmark().start();
	}

	public void start() {
		this.init();
		this.startClients();
		this.populateExpData();
		this.searchExpData();
		this.sm.shutdown();
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
