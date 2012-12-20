/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.event.AfterActiveEvent;
import com.fs.commons.api.event.ListenerI;
import com.fs.gridservice.commons.api.GridFacadeI;
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

	@Override
	public void setUp() {
		sm = SPIManagerI.FACTORY.get();
		sm.getContainer().getEventBus()
				.addListener(AfterActiveEvent.class, new ListenerI<AfterActiveEvent>() {

					@Override
					public void handle(AfterActiveEvent t) {
						//
						Object obj = t.getSource();
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
	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	public void tearDown() throws Exception {
		sm.shutdown();//
	}

}
