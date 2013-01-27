/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.mock.MockClientWrapper;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.impl.test.GsCommonsTestSPI;
import com.fs.websocket.api.mock.WSClientManager;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected SPIManagerI sm;

	protected ContainerI container;

	protected GridFacadeI facade;

	protected WSClientManager<MockClientWrapper> factory;

	protected SessionManagerI smanager;

	protected ClientManagerI cmanager;

	protected TerminalManagerI tmanager;

	@Override
	public void setUp() {
		sm = SPIManagerI.FACTORY.get();

		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.facade = sm.getContainer().find(GridFacadeI.class, true);

		factory = WSClientManager.newInstance(GsCommonsTestSPI.DEFAULT_WS_URI, MockClientWrapper.class,
				this.container);
		this.smanager = this.container.find(SessionManagerI.class, true);
		this.cmanager = facade.getEntityManager(ClientManagerI.class);
		this.tmanager = facade.getEntityManager(TerminalManagerI.class);
		//
		// assertTrue("data grid should empty by destroyALl.", this.facade
		// .getDataGrid().isEmpty());

	}

	protected MockClientWrapper newClientAndAuth(String accId) throws Exception {
		PropertiesI<Object> cre = new MapProperties<Object>();
		cre.setProperty(SessionGd.ACCID, accId);//
		MockClientWrapper rt = this.factory.createClient(true);
		rt.auth(cre);
		return rt;

	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	public void tearDown() throws Exception {
		sm.shutdown();//
	}

}
