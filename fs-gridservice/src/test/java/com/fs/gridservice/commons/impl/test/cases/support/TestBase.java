/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.impl.test.mock.MockClient;
import com.fs.gridservice.commons.impl.test.mock.MockClientFactory;
import com.fs.gridservice.commons.impl.test.mock.MockEventDriveClient;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected SPIManagerI sm;

	protected ContainerI container;

	protected GridFacadeI facade;

	protected MockClientFactory factory;

	protected SessionManagerI smanager;
	
	protected ClientManagerI cmanager;

	protected TerminalManagerI tmanager;
	@Override
	public void setUp() {
		sm = SPIManagerI.FACTORY.get();

		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.facade = sm.getContainer().find(GridFacadeI.class, true);

		factory = new MockClientFactory(this.container).start();
		this.smanager = this.container.find(SessionManagerI.class, true);
		this.cmanager = facade.getEntityManager(ClientManagerI.class);
		this.tmanager = facade.getEntityManager(TerminalManagerI.class);
		//
		// assertTrue("data grid should empty by destroyALl.", this.facade
		// .getDataGrid().isEmpty());

	}

	protected MockClient newClientAndAuth(String accId) throws Exception {

		return this.factory.newClientAndAuth(accId);

	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	protected MockEventDriveClient newEventDriveClient(String accId, boolean start) throws Exception {
		MockClient mc = this.newClientAndAuth(accId);

		MockEventDriveClient rt = new MockEventDriveClient(mc);
		if (start) {
			rt.start();
		}
		return rt;

	}

	public void tearDown() throws Exception {
		sm.shutdown();//
	}

}
