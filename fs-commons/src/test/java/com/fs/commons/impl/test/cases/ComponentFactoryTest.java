/**
 *  
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.components.ComponentFactoryI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ComponentFactoryTest extends TestBase {

	public void testComponentFactory() {
		ComponentFactoryI cf = this.container.find(ComponentFactoryI.class);
		assertNotNull("cf not found", cf);
	}
}
