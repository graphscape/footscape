/**
 * Jul 17, 2012
 */
package com.fs.uicore.impl.test.handler;

import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.HandlerSupport;
import com.fs.uicore.impl.test.gwt.client.cases.ErrorHandleTest;

/**
 * @author wu
 * 
 */
public class ErrorTestHandler extends HandlerSupport {

	public void handleTestThrowable(RequestI req, ResponseI res) {
		throw new FsException(ErrorHandleTest.MSG);
	}
}
