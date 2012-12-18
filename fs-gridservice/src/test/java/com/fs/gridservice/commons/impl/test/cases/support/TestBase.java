/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.InterceptorI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.data.SessionGd;
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
		sm.addInterceptor(new InterceptorI() {

			@Override
			public void beforeActive(ActivableI obj) {

			}

			@Override
			public void afterActive(ActivableI obj) {
				if (obj instanceof DgFactoryI) {
					DataGridI dg = ((DgFactoryI) obj).getInstance();
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

	}

	protected MockClient newClient(String accId) throws Exception {
		SessionGd s = this.newSession(accId);
		return this.factory.newClient(s.getId());

	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	protected MockEventDriveClient newEventDriveClient(String accId) throws Exception {
		MockClient mc = this.newClient(accId);

		return new MockEventDriveClient(mc);

	}

	protected SessionGd newSession(String accId) {
		PropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty("accountId", accId);
		SessionGd s = smanager.createSession(pts);
		// assertThe Session is shared with Grid.
		String sid = s.getId();
		return s;
	}

	public void tearDown() throws Exception {
		sm.shutdown();//
	}

}
