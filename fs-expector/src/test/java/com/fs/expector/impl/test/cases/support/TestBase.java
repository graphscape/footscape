/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.InterceptorI;
import com.fs.commons.api.SPIManagerI;
import com.fs.datagrid.api.DataGridI;
import com.fs.datagrid.api.DgFactoryI;
import com.fs.expector.api.GridFacadeI;

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
	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	public void tearDown() throws Exception {
		sm.shutdown();//
	}

}
