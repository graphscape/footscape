/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class CooperRequestAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public CooperRequestAP(ContainerI c) {
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
		ExpSearchViewI sm = mc.openExpSearch(false);

		UserExpModel ue = sm.getExpId(false);
		if(ue == null){
			Window.alert("Please select your expectation to cooperate with!");
			return;
		}
		String expId1 = ue.getExpId();

		// CooperControlI cc= c.getManager().find(CooperControlI.class, true);
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/request"));
		req.getPayloads().setProperty("expId1", (expId1));
		req.getPayloads().setProperty("expId2", (expId2));
		this.sendMessage(req);
	}

}
