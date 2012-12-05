/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.dataservice.api.expapp.wrapper2;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfig;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 *         <p>
 *         this node is for easy query.
 */
public class UserActivity extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("user-activity");

	public static final String PK_ACCOUNT_ID = "accountId";

	public static final String PK_ACTIVITY_ID = "activityId";

	/**
	 * @param type
	 */
	public UserActivity() {
		super(TYPE);
	}

	public static void config(NodeConfigurations cfs) {
		NodeConfig nc = cfs.addConfig(TYPE, UserActivity.class)
				.field(PK_ACCOUNT_ID).field(PK_ACTIVITY_ID);

	}

	public void setAccountId(String accId) {
		this.setProperty(PK_ACCOUNT_ID, accId);
	}

	public void setActivityUid(String accId) {
		this.setProperty(PK_ACTIVITY_ID, accId);
	}

}
