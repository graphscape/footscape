/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps.item;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class CooperAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		// find the coper model and perform action
		Mvc mvc = (Mvc) c.getManager().getControl(MainControlI.class, true)
				.getLazyObject(MainControlI.LZ_COOPER, true);

		CooperModelI cm = mvc.getModel();
		ExpItemModel eim = (ExpItemModel) c.getModel();
		String expId2 = eim.getExpId();
		cm.coperExpId2(expId2);

		//
		ExpSearchModelI sm = (ExpSearchModelI) eim.getParent();
		String expId = sm.getExpId(true);

		cm.coperExpId1(expId);
		// this is just forward to CoperContorl's submit action
		ControlUtil.triggerAction(cm, CooperModelI.A_REQUEST);

		// CooperControlI cc= c.getManager().find(CooperControlI.class, true);

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
