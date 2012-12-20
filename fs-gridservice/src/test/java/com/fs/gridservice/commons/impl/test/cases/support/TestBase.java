/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.event.AfterActiveEvent;
import com.fs.commons.api.event.ListenerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.impl.test.mock.MockClient;
import com.fs.gridservice.commons.impl.test.mock.MockClientFactory;
import com.fs.gridservice.commons.impl.test.mock.MockEventDriveClient;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.DgFactoryI;

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

	@Override
	public void setUp() {
		sm = SPIManagerI.FACTORY.get();
		sm.getContainer()
				.getEventBus()
				.addListener(AfterActiveEvent.class,
						new ListenerI<AfterActiveEvent>() {

							@Override
							public void handle(AfterActiveEvent t) {
								Object obj = t.getSource();//
								if (obj instanceof DgFactoryI) {
									DataGridI dg = ((DgFactoryI) obj)
											.getInstance();
									dg.destroyAll();// NOTE clean
													// memory.
								}

							}
						});

		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.facade = sm.getContainer().find(GridFacadeI.class, true);

		factory = new MockClientFactory(this.container).start();
		smanager = this.container.find(SessionManagerI.class, true);

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

	protected MockEventDriveClient newEventDriveClient(String accId,
			boolean start) throws Exception {
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
