/**
 * Jun 22, 2012
 */
package com.fs.uiserver.impl.test.cases;

import org.junit.Test;

import com.fs.uiserver.impl.test.UiServerImplTestSPI;
import com.fs.uiserver.impl.test.cases.support.TestBase;

/**
 * @author wu
 * @see res:/ftl/uitest/cfgf/...
 */
public class CfgfTest extends TestBase {

	@Test
	public void testCfgf() {
		this.runScenario(UiServerImplTestSPI.class.getName() + ".test-cfgf");
		
	}
	
	

}
