/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.api.expapp.NodeTypes;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;

/**
 * @author wu
 * 
 */
public class Activity extends NodeWrapper {

	public static final String COOPER_CONFIRM_UID = "cooperConfirmUid";

	public static final String[] FNS = new String[] { COOPER_CONFIRM_UID };

	/**
	 * @param ntype
	 * @param pts
	 */
	public Activity() {
		super(NodeTypes.ACTIVITY);
	}

	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(NodeTypes.ACTIVITY, Activity.class).field(
				COOPER_CONFIRM_UID);
	}

	public String getCooperConfirmUid() {
		return (String) this.getProperty(COOPER_CONFIRM_UID);
	}

	public void setCooperConfirmUid(String uid) {
		this.setProperty(COOPER_CONFIRM_UID, uid);
	}

	public List<Expectation> getExpectationList() {
		NodeQueryOperationI<ExpActivity> op = this.dataService
				.prepareNodeQuery(ExpActivity.TYPE);
		op.propertyEq(ExpActivity.PK_ACTIVITY_ID, this.getId());//TODO how about multiple same id?
		//
		NodeQueryResultI<ExpActivity> rst = op.execute().getResult().assertNoError();
		List<ExpActivity> eaL = rst.list();
		List<Expectation> rt = new ArrayList<Expectation>();
		for (ExpActivity ea : eaL) {
			Expectation ex = this.dataService.getNewestById(Expectation.class,
					ea.getExpId(), true);

			rt.add(ex);

		}
		return rt;
	}
}
