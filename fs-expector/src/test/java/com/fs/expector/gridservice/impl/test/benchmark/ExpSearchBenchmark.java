/**
 *  Jan 22, 2013
 */
package com.fs.expector.gridservice.impl.test.benchmark;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fs.commons.api.client.BClientFactoryI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.ExpectorGsTestSPI;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;
import com.fs.webcomet.api.WebCometComponents;
import com.fs.websocket.api.mock.WSClientRunner;

/**
 * @author wuzhen
 * 
 */
public class ExpSearchBenchmark extends WSClientRunner<MockExpectorClient> {

	protected DataServiceI dataService;

	protected int expCountForEachUser;

	public ExpSearchBenchmark(BClientFactoryI.ProtocolI pro, URI uri, int initClients, int con,
			int maxSearch, int expCount) {
		super(pro, uri, MockExpectorClient.class, initClients, con, maxSearch, -1);
		this.expCountForEachUser = expCount;

	}

	@Override
	public void init() {
		super.init();

		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

	}

	@Override
	protected void initClients() {
		super.initClients();

		LOG.info("after init clients,to populate exp for each client");
		List<MockExpectorClient> cL = this.clients.getClientList();
		for (int i = 0; i < cL.size(); i++) {
			MockExpectorClient c = cL.get(i);
			String user = "user" + i;
			for (int j = 0; j < this.expCountForEachUser; j++) {
				String body1 = user + " is exepecting number" + j + ",what is yours,";
				c.newExp(body1);
			}
		}
		LOG.info("done of populating exp for each client");

	}

	@Override
	protected MockExpectorClient createClient(int idx) {
		String email = "user-" + idx + "@some.com";
		String nick = "user-" + idx;

		return TestBase.newClient(WebCometComponents.AJAX, this.clients, email, nick);
	}

	/*
	 * Jan 28, 2013
	 */
	@Override
	protected void work(int idx) {

		// LOG.info("in stage of search:" + );

		int idx1 = idx % this.initClients;
		int idx2 = idx % this.expCountForEachUser;

		MockExpectorClient c = this.clients.getRandomClient();

		String phrase = "user" + idx1 + " expecting number" + idx2;

		c.search(false, 0, 10, null, phrase, 3);

	}

}
