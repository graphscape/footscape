/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 7, 2012
 */
package com.fs.engine.impl.test.handler;

import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class TestHandler5 extends HandlerSupport {

	public void handleThrow() {
		throw new FsException("for test");
	}

}
