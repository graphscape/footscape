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

	private String expId1;

	private String expId2;

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
	public void cooper(String expId1, String expId2) {
		this.expId1 = expId1;
		this.expId2 = expId2;
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

	@Override
	public String getExpId1() {
		return this.expId1;
	}

	@Override
	public String getExpId2() {
		return this.expId2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.coper.CooperModelI#incomingCr(com.fs.uiclient
	 * .api.gwt.client.coper.IncomingCrModel)
	 */
	@Override
	public void incomingCr(IncomingCrModel crm) {
		this.child(crm);
	}

}
