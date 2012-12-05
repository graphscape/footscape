/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.test.mock;

import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wu
 * 
 */
public class MockUserActivity {

	public String actId;
	public String accId;//account

	/*
	 * Nov 3, 2012
	 */
	@Override
	public boolean equals(Object obj) {
		//
		if (obj == null || !(obj instanceof MockUserActivity)) {
			return false;
		}
		MockUserActivity ma = (MockUserActivity) obj;

		return ObjectUtil.nullSafeEquals(actId, ma.actId)
				&& ObjectUtil.nullSafeEquals(accId, ma.accId);
	}

}
