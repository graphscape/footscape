/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 18, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory.Configuration;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class LoggerTest extends TestBase {

	public void testLogger() {
		String prefix = "com.test";
		String name = prefix + ".yes.log1";
		UiLoggerI log = UiLoggerFactory.getLogger(name);

		Configuration cfg = UiLoggerFactory.getConfiguration4Logger(name);
		assertNotNull(cfg);

		assertEquals("default level should be info", UiLoggerI.LEVEL_INFO,
				cfg.getLoggerLevel());

		log.debug("should not be logged for debug level");

		UiLoggerFactory.configure(prefix, UiLoggerI.LEVEL_DEBUG);
		cfg = UiLoggerFactory.getConfiguration4Logger(name);
		assertEquals("log level of " + name + " should be debug",
				UiLoggerI.LEVEL_DEBUG, cfg.getLoggerLevel());

		log.debug("should be logged after configured");

	}
}
