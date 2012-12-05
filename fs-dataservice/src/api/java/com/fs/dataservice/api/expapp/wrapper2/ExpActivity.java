/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.dataservice.api.expapp.wrapper2;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.api.expapp.wrapper.Expectation;

/**
 * @author wu
 * @see CooperConfirmOpeartionI
 */
public class ExpActivity extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("exp-activity");

	public static final String PK_EXP_ID = "expId";

	public static final String PK_ACTIVITY_ID = "activityId";

	public static final String PK_ACCOUNT_ID = "accountId";

	/**
	 * @param type
	 */
	public ExpActivity() {
		super(TYPE);
	}

	public static void config(NodeConfigurations cfs) {

		cfs.addConfig(TYPE, ExpActivity.class).field(PK_EXP_ID)
				.field(PK_ACCOUNT_ID).field(PK_ACTIVITY_ID);

	}

	public void setAccountId(String accId) {
		this.setProperty(PK_ACCOUNT_ID, accId);
	}

	public void setActivityId(String accUid) {
		this.setProperty(PK_ACTIVITY_ID, accUid);
	}

	public void setExpId(String accUid) {
		this.setProperty(PK_EXP_ID, accUid);
	}

	public String getActivityId() {
		return this.getPropertyAsString(PK_ACTIVITY_ID);

	}

	public String getAccountId() {
		return this.getPropertyAsString(PK_ACCOUNT_ID);

	}

	public String getExpId() {
		return this.getPropertyAsString(PK_EXP_ID);

	}

	/**
	 * Oct 30, 2012
	 */
	public Expectation getExpectation() {

		return this.dataService.getNewestById(Expectation.class, this.getExpId(), true);

	}

}
