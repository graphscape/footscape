/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.activity;

import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ParticipantModel extends ModelSupport {

	public static final Location L_EXP_ID = Location.valueOf("expId");

	public static final Location L_ACCOUNT_ID = Location.valueOf("accountId");

	/**
	 * @param name
	 */
	public ParticipantModel(String name, String expId, String accId) {
		super(name);
		this.setValue(L_EXP_ID, expId);
		this.setValue(L_ACCOUNT_ID, accId);
	}

	public String getExpId() {
		return this.getValue(String.class, L_EXP_ID);
	}

	public String getAccountId() {
		return this.getValue(String.class, L_ACCOUNT_ID);
	}

	/**
	 * Oct 24, 2012
	 */
	public boolean isExpId(String expId) {
		return this.getExpId().equals(expId);
	}

}
