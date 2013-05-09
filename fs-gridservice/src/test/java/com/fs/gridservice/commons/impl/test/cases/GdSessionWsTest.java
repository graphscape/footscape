/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.impl.test.cases;

import com.fs.gridservice.commons.impl.test.cases.support.GdSessionTestBase;
import com.fs.websocket.api.Components;

/**
 * @author wuzhen
 * 
 */
public class GdSessionWsTest extends GdSessionTestBase {

	/**
	 * @param protocol
	 */
	public GdSessionWsTest() {
		super(Components.WEBSOCKET);
	}

	public void testSession() throws Exception {
		this.doTestSession();
	}

}
