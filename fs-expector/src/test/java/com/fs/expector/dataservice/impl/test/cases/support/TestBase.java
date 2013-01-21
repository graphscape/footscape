/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.dataservice.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.SPIManagerI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DumpOperationI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected SPIManagerI sm;

	protected DataServiceI datas;

	@Override
	public void setUp() {
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");

		DataServiceFactoryI dsf = sm.getContainer().finder(DataServiceFactoryI.class).find(true);
		this.datas = dsf.getDataService();//
	}

	protected void dump() {
		System.out.println("\n\ndump:\n");
		this.datas.prepareOperation(DumpOperationI.class).execute().getResult().assertNoError();
	}

	public void tearDown() throws Exception {

	}

}
