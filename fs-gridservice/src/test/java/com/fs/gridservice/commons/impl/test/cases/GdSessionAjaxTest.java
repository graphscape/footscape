/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.impl.test.cases;

import com.fs.gridservice.commons.impl.test.cases.support.GdSessionTestBase;
import com.fs.webcomet.api.WebCometComponents;

/**
 * @author wuzhen
 * 
 */
public class GdSessionAjaxTest extends GdSessionTestBase {

	/**
	 * @param protocol
	 */
	public GdSessionAjaxTest() {
		super(WebCometComponents.AJAX);
	}

	public void testSession() throws Exception {
		this.doTestSession();
	}

}
