/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 29, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class CooperRequest extends NodeWrapper {

	public static final String EXP_ID1 = "expId1";

	public static final String EXP_ID2 = "expId2";

	public static final String ACCOUNT_ID1 = "accountId1";

	public static final String ACCOUNT_ID2 = "accountId2";

	/**
	 * @param ntype
	 */
	public CooperRequest() {
		super(NodeTypes.COOPER_REQUEST);
	}

	public void setExpId1(String expId) {
		this.setProperty(EXP_ID1, expId);

	}

	public String getExpId1() {
		return (String) this.getProperty(EXP_ID1);
	}

	public String getExpId2() {
		return (String) this.getProperty(EXP_ID2);
	}

	public void setExpId2(String expId) {
		this.setProperty(EXP_ID2, expId);

	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(NodeTypes.COOPER_REQUEST, CooperRequest.class).field(EXP_ID1).field(EXP_ID2)
				.field(ACCOUNT_ID2).field(ACCOUNT_ID1);

	}

	public void setAccountId1(String accId1) {
		this.setProperty(ACCOUNT_ID1, accId1);
	}

	public String getAccountId1() {
		return this.getPropertyAsString(ACCOUNT_ID1);
	}

	public void setAccountId2(String accId2) {
		this.setProperty(ACCOUNT_ID2, accId2);
	}

	public String getAccountId2() {
		return this.getPropertyAsString(ACCOUNT_ID2);
	}

}
