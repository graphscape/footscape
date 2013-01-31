/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ItemCooperAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public ItemCooperAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		// find the coper model and perform action
		ControlManagerI mgr = ae.getSource().getClient(true).getChild(ControlManagerI.class, true);

		MainControlI mc = mgr.getControl(MainControlI.class, true);

		String expId2 = (String) ae.getProperty("expId2", true);

		//
		ExpSearchModelI sm = mc.getExpSearchModel();

		String expId1 = sm.getExpId(true);

		// this is just forward to CoperContorl's submit action
		new ActionEvent(ae.getSource(), Actions.A_COOP_REQUEST).property("expId1", expId1)
				.property("expId2", expId2).dispatch();

		// CooperControlI cc= c.getManager().find(CooperControlI.class, true);

	}

}
