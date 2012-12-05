/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.test.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class TestHandler0 extends HandlerSupport {

	private static final Logger LOG = LoggerFactory
			.getLogger(TestHandler0.class);
	public static final String RESPONSE = "DEFAULT handler ";

	/**
	 * @param cfg
	 */
	public TestHandler0() {
		super();
	}

	@Override
	public void handle(HandleContextI sc) {
		LOG.warn("this is default.");
		sc.getFilterContext().getResponse().setPayload(RESPONSE);
	}

}
