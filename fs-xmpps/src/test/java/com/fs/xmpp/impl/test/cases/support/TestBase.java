/**
 * Jun 10, 2012
 */
package com.fs.xmpp.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.xmpps.api.XmppI;

/**
 * @author wu
 * @see RestTestBase
 */
public class TestBase extends TestCase {

	protected static SPIManagerI SPIM;

	protected XmppI xmpp;

	protected ContainerI container;

	@Override
	public void setUp() {
		if (SPIM == null) {
			SPIM = SPIManagerI.FACTORY.get();
			SPIM.load("/boot/test-spim.properties");
		}
		this.container = SPIM.getContainer();
		xmpp = SPIM.getContainer().find(XmppI.class, true);

	}

	public void tearDown() throws Exception {
		// this.neo.shutdown();//
	}

}
