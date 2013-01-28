/**
 *  Jan 22, 2013
 */
package com.fs.expector.gridservice.impl.test.benchmark;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.ExpectorGsTestSPI;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;
import com.fs.websocket.api.mock.WSClientRunner;

/**
 * @author wuzhen
 * 
 */
public class ExpSearchBenchmark extends WSClientRunner<MockExpectorClient> {

	protected DataServiceI dataService;

	protected int clientCount;

	protected AtomicInteger nextClient;

	protected AtomicBoolean expStage;

	protected AtomicInteger nextSearch;

	protected int expCountForEachUser;

	public ExpSearchBenchmark(int cc, int ec, int maxSearch, int dure) {
		super(ExpectorGsTestSPI.DEFAULT_WS_URI, MockExpectorClient.class, 1, cc + 1 + maxSearch, dure);
		this.nextClient = new AtomicInteger();
		this.nextSearch = new AtomicInteger();
		this.expStage = new AtomicBoolean();
		this.clientCount = cc;
		this.expCountForEachUser = ec;

	}

	@Override
	public void init() {
		super.init();

		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

	}

	/*
	 * Jan 28, 2013
	 */
	@Override
	protected void work() {

		if (this.clients.size() < this.clientCount) {
			int idx = this.nextClient.getAndIncrement();
			LOG.info("in stage of create client:" + idx);

			String email = "user-" + idx + "@some.com";
			String nick = "user-" + idx;

			TestBase.newClient(this.clients, email, nick);
			return;
		}
		if (!this.expStage.get()) {
			LOG.info("in stage of populate exp for each client");
			List<MockExpectorClient> cL = this.clients.getClientList();
			for (MockExpectorClient c : cL) {
				String accId = c.getAccountId();
				for (int i = 0; i < this.expCountForEachUser; i++) {
					String body1 = accId + " is exepecting number" + i + ",what is yours,";
					c.newExp(body1);
				}
			}
			this.expStage.set(true);
			return;
		}

		LOG.info("in stage of search:" + this.nextSearch.getAndIncrement());

		MockExpectorClient c = this.clients.getRandomClient();
		String phrase = "expecting number1";
		c.search(false, 0, 10, null, phrase, 3);

	}

}
