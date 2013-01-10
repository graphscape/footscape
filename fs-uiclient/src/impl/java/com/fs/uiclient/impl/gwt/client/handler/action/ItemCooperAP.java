/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;

/**
 * @author wu
 * 
 */
public class ItemCooperAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		// find the coper model and perform action
		ControlManagerI mgr = ae.getSource().getClient(true)
				.getChild(ControlManagerI.class, true);

		MainControlI mc = mgr.getControl(MainControlI.class, true);

		Mvc mvc = (Mvc) mc.getLazyObject(MainControlI.LZ_COOPER, true);

		CooperModelI cm = mvc.getModel();
		ControlI c = (ControlI)ae.getSource();
		ExpItemModel eim = (ExpItemModel) c.getModel();
		String expId2 = eim.getExpId();

		//
		ExpSearchModelI sm = (ExpSearchModelI) eim.getParent();
		String expId1 = sm.getExpId(true);

		cm.cooper(expId1, expId2);
		// this is just forward to CoperContorl's submit action
		ControlUtil.triggerAction(cm, Actions.A_COOP_REQUEST);

		// CooperControlI cc= c.getManager().find(CooperControlI.class, true);

	}

}
