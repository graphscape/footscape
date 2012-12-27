/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.expector.dataservice.api.wrapper2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfig;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 *         <p>
 *         this node is for easy query.
 */
public class UserSnapshot extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("user-snapshot");

	public static final String PK_ACCOUNT_ID = "accountId";//

	public static final String PK_ACTIVITY_ID_CSV = "activityIdCsv";//

	public static final String PK_EXP_ID_CSV = "expIdCsv";//

	public static final String PK_COOPER_REQUEST_ID_CSV = "cooperRequestIdCsv";// in
																				// requesting
																				// not
																				// processed.

	/**
	 * @param type
	 */
	public UserSnapshot() {
		super(TYPE);
	}

	public static void config(NodeConfigurations cfs) {
		NodeConfig nc = cfs.addConfig(TYPE, UserSnapshot.class)
				.field(PK_ACCOUNT_ID).field(PK_ACTIVITY_ID_CSV)
				.field(PK_COOPER_REQUEST_ID_CSV).field(PK_EXP_ID_CSV);

	}

	public String getAccountId() {
		return (String) this.getProperty(PK_ACCOUNT_ID);
	}

	public void setAccountId(String accId) {
		this.setProperty(PK_ACCOUNT_ID, accId);
	}

	public List<String> getExpIdList() {
		String csv = this.getPropertyAsString(PK_EXP_ID_CSV);

		return this.csvToList(csv);
	}

	public List<String> getActivityIdList() {
		String csv = this.getPropertyAsString(PK_ACTIVITY_ID_CSV);

		return this.csvToList(csv);
	}

	public List<String> getCooperRequestIdList() {
		String csv = this.getPropertyAsString(PK_COOPER_REQUEST_ID_CSV);

		return this.csvToList(csv);
	}

	private List<String> csvToList(String csv) {
		List<String> rt = new ArrayList<String>();
		if (csv == null || csv.length() == 0) {
			return rt;
		}

		String[] arr = csv.split(",");

		rt.addAll(Arrays.asList(arr));
		return rt;

	}
}
