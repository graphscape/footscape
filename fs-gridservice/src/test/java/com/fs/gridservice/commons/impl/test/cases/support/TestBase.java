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
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.MockClientFactory;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;

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

		factory = MockClientFactory.getInstance(this.container);
		this.smanager = this.container.find(SessionManagerI.class, true);
		this.cmanager = facade.getEntityManager(ClientManagerI.class);
		this.tmanager = facade.getEntityManager(TerminalManagerI.class);
		//
		// assertTrue("data grid should empty by destroyALl.", this.facade
		// .getDataGrid().isEmpty());

	}

	protected MockClient newClientAndAuth(String accId) throws Exception {

		return this.factory.newClient().auth(accId);

	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	protected MockClient newEventDriveClient(String accId, boolean start)
			throws Exception {
		MockClient mc = this.newClientAndAuth(accId);

		if (start) {
			mc.startEventDrive();
		}
		return mc;

	}

	public void tearDown() throws Exception {
		sm.shutdown();//
	}

}
