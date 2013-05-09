/**
 *  Feb 6, 2013
 */
package com.fs.gridservice.commons.impl.test.cases;

import com.fs.commons.api.client.BClientFactoryI.ProtocolI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class EventTimeoutTest extends TestBase {

	/**
	 * @param protocol
	 */
	public EventTimeoutTest(ProtocolI protocol) {
		super(protocol);
	}

	public void test() {
		EventGd e = new EventGd(EventType.valueOf("timeout-testing"),
				Path.valueOf("/events/timeout/testing"), Path.valueOf("/timeout/testing"));

		this.facade.getGlogalEventQueue().offer(e);
	}
}
