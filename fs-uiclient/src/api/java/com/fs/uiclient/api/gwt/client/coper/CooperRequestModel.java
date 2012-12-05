/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.uiclient.api.gwt.client.coper;

import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class CooperRequestModel extends ModelSupport {

	private String expId1;

	private String expId2;

	private String accountId;

	/**
	 * @param name
	 */
	public CooperRequestModel(String name) {
		super(name);
	}

	/**
	 * @return the expId1
	 */
	public String getExpId1() {
		return expId1;
	}

	/**
	 * @param expId1
	 *            the expId1 to set
	 */
	public void setExpId1(String expId1) {
		this.expId1 = expId1;
	}

	/**
	 * @return the expId2
	 */
	public String getExpId2() {
		return expId2;
	}

	/**
	 * @param expId2
	 *            the expId2 to set
	 */
	public void setExpId2(String expId2) {
		this.expId2 = expId2;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
