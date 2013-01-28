/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.test.cases.support;

import junit.framework.TestCase;

import org.junit.Before;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.ExpectorGsTestSPI;
import com.fs.websocket.api.mock.WSClientManager;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {
	protected SPIManagerI sm;
	protected ContainerI container;

	protected DataServiceI dataService;

	protected MessageServiceI engine;

	protected WSClientManager<MockExpectorClient> clients;

	@Before
	public void setUp() {
		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

		this.clients = WSClientManager.newInstance(ExpectorGsTestSPI.DEFAULT_WS_URI,
				MockExpectorClient.class, this.container);

	}

	protected MockExpectorClient newClient(String email, String nick) {// anonymous
		return newClient(this.clients, email, nick);
	}

	public static MockExpectorClient newClient(WSClientManager<MockExpectorClient> clients, String email,
			String nick) {// anonymous
		PropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty(MockExpectorClient.SIGNUP_AT_CONNECT, true);
		pts.setProperty(MockExpectorClient.AUTH_WITH_SIGNUP, true);

		pts.setProperty(MockExpectorClient.SIGNUP_EMAIL, email);
		pts.setProperty(MockExpectorClient.SIGNUP_NICK, nick);
		pts.setProperty(MockExpectorClient.SIGNUP_PASS, nick);

		MockExpectorClient rt = clients.createClient(true, pts);
		return rt;
	}

	protected void dumpDb() {
		DumpOperationI op = this.dataService.prepareOperation(DumpOperationI.class);
		op.execute().getResult().assertNoError();
	}

}
