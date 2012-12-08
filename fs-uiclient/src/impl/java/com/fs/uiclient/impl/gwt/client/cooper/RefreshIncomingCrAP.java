/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.CooperRequestModel;
import com.fs.uiclient.api.gwt.client.util.ListDataUtil;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 *         <p>
 *         Snapshot notify new crList:which will cause 1)some cr should
 *         delete,those are confirmed already. 2)the new created detail should
 *         be got from this AP.
 *         <p>
 *         New created got and add to the child of the CooperModelI.
 *         UserExpListControlI should monitor this event,including deleting and
 *         adding the CR here.
 *         <p>
 * 
 */
public class RefreshIncomingCrAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		CooperModelI cm = c.getModel();
		List<String> crIdL = cm.getNewIncomingCrIdList();
		req.getPayloads().setProperty("cooperRequestIdList",
				ListDataUtil.toStringDataList(crIdL));

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//
		CooperModelI cm = c.getModel();
		ListData<ObjectPropertiesData> crL = (ListData<ObjectPropertiesData>) res
				.getPayload("cooperRequestList", true);

		for (int i = 0; i < crL.size(); i++) {
			ObjectPropertiesData od = crL.get(i);
			StringData crId = (StringData) od.getProperty("id");
			StringData expId1 = (StringData) od.getProperty("expId1");
			StringData expId2 = (StringData) od.getProperty("expId2");
			StringData accountId1 = (StringData) od.getProperty("accountId1");
			StringData accountId2 = (StringData) od.getProperty("accountId2");

			CooperRequestModel crm = new CooperRequestModel(crId.getValue());
			crm.setAccountId1(accountId1.getValue());
			crm.setAccountId2(accountId2.getValue());
			crm.setExpId1(expId1.getValue());
			crm.setExpId2(expId2.getValue());
			crm.commit();
			cm.child(crm);// add as the child,this is monitored by uelist
							// control.

		}
	}

}
