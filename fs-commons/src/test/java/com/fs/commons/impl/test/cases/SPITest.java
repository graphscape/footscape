/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.impl.test.TestSPI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SPITest extends TestBase {

	public void testSPI() throws Exception {
		TestSPI spi = (TestSPI) this.sm.getSpi(TestSPI.class.getName(), true);

	}

}
