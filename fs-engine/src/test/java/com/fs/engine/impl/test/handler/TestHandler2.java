/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.test.handler;

import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class TestHandler2 extends HandlerSupport {
	public static final String HD_ISTHROW = "isThrow";

	public static final String THROW_MSG = "test exception throwed";

	public static final String MSG = "this is test handler2";

	/**
	 * @param cfg
	 */
	public TestHandler2() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HandleContextI sc) {
		boolean thow = sc.getFilterContext().getRequest().getHeaders()
				.getPropertyAsBoolean(HD_ISTHROW, false);
		if (thow) {
			throw new FsException(THROW_MSG);
		} else {
			sc.getFilterContext().getResponse().setPayload(MSG);
		}

	}

}
