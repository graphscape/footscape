/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
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
		UserInfo ui = this.getEndpoint().getUserInfo();
		if (ui.isAnonymous()) {
			boolean login = Window
					.confirm("You must log in before make the request, do you want to login in?");
			if (!login) {
				return;// do nothing
			}
			mc.openLoginView(true);
			return;//anonymous user should not continue;
		}
		// is register user
		if (ue == null) {
			boolean sel = Window
					.confirm("You must select one of your expectation before make the request, do you want to select one?");
			if (!sel) {
				return;// do nothing
			}

			// open my expecation list
			mc.openUeList(true);
			return;//if not select one exp, not continue;
		}

		String expId1 = ue.getExpId();

		// CooperControlI cc= c.getManager().find(CooperControlI.class, true);
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/request"));
		req.getPayloads().setProperty("expId1", (expId1));
		req.getPayloads().setProperty("expId2", (expId2));
		this.sendMessage(req);
	}

}
