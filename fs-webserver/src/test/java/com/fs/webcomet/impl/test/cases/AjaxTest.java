/**
 *  Dec 11, 2012
 */
package com.fs.webcomet.impl.test.cases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webcomet.impl.test.support.CometTestBase;

/**
 * @author wuzhen
 * 
 */
public class AjaxTest extends CometTestBase {

	/**
	 * @param protocol
	 */
	public AjaxTest() {
		super("ajax");
	}

	private static final Logger LOG = LoggerFactory.getLogger(AjaxTest.class);

	private static final boolean srmac = false;

	public void testClients() throws Exception {
		super.doTestClients();
		
	}

}
