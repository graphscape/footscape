/**
 *  Dec 20, 2012
 */
package com.fs.gridservice.commons.impl.test.mock.chat;

/**
 * @author wuzhen
 * 
 */
public class MockParticipant {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	private String accountId;

	@Override
	public String toString() {
		return "accId:" + this.accountId + ",pid:" + this.id;
	}

}
