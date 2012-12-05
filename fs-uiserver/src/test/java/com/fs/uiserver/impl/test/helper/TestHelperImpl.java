/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.test.helper;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.lang.FsException;
import com.fs.uiserver.TestHelperI;

/**
 * @author wu
 * 
 */
public class TestHelperImpl implements TestHelperI {

	public Map<String, String> confirmCodeMap = new HashMap<String, String>();

	/*
	 * Nov 3, 2012
	 */
	@Override
	public void sendConfirmCode(String email, String cc) {
		this.confirmCodeMap.put(email, cc);
	}

	/*
	 * Nov 3, 2012
	 */
	@Override
	public String getConfirmCode(String email, boolean force) {
		//
		String rt = this.confirmCodeMap.get(email);
		if (force && rt == null) {
			throw new FsException("no confirmcode found for:" + email
					+ ",avaliable ones:" + this.confirmCodeMap);
		}
		return rt;

	}
}
