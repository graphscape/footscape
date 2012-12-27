/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import org.junit.Test;

import com.fs.expector.gridservice.impl.test.ExpectorGsTestSPI;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * @see res:/ftl/uitest/cfgf/...
 */
public class CfgfTest extends TestBase {

	@Test
	public void testCfgf() {
		this.runScenario(ExpectorGsTestSPI.class.getName() + ".test-cfgf");
		
	}
	
	

}
