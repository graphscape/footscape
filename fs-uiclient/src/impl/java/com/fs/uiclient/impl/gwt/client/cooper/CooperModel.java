/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.IncomingCrModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 *         <p>
 *         this is a "master" model,for 1)cooper request,2)cooper
 *         confirm,3)refresh and maintains the data of both.
 */
public class CooperModel extends ModelSupport implements CooperModelI {

	/**
	 * @param name
	 */

	public CooperModel(String name) {
		super(name);

		ControlUtil.addAction(this, CooperModel.A_REQUEST);
		ControlUtil.addAction(this, CooperModel.A_CONFIRM);
		ControlUtil.addAction(this, CooperModel.A_REFRESH_INCOMING_CR);

	}

	@Override
	public void coperExpId1(String expId) {
		this.setValue(L_EXPID1, expId);
	}

	@Override
	public void coperExpId2(String expId) {
		this.setValue(L_EXPID2, expId);
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public void cooperRequestId(String crId) {
		this.setValue(L_COOPERREQUEST_ID, crId);
	}

	@Override
	public List<IncomingCrModel> getIncomingCooperRequestModelList() {
		//
		return this.getChildList(IncomingCrModel.class);

	}

}
