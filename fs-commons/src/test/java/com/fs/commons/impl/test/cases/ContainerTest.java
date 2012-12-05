/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.impl.test.cases.support.TestBase;
import com.fs.commons.impl.test.config.TestConfigurable;
import com.fs.commons.impl.test.config.TestConfigurable2;

/**
 * @author wuzhen
 * 
 */
public class ContainerTest extends TestBase {

	public void testContainer() {

		ContainerI c = sm.getContainer();
		// find commons

		// find all
		List<Object> oL = c.finder(Object.class).find();
		Class[] expectedServiceL = new Class[] { ConfigFactoryI.class,
				ContainerI.FactoryI.class, TestConfigurable.class,
				TestConfigurable2.class };

		assertEquals("total object count check failed",
				expectedServiceL.length, oL.size());
		//
		TestConfigurable tc = c.find(TestConfigurable.class, true);

		String value1 = tc.getConfiguration().getProperty("name1");
		assertEquals("value1", value1);

		//
		TestConfigurable2 tc2 = c.find(TestConfigurable2.class, true);

		//

	}
	
	public void testFinder(){
		
	}
}
