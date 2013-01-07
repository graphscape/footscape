/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *         <p>
 *         Send request for cooperation with another exp.
 */
public class CooperRequestAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();
		CooperModelI cm = c.getModel();
		String id1 = cm.getExpId1();
		String id2 = cm.getExpId2();
		
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/request"));
		req.getPayloads().setProperty("expId1", (id1));
		req.getPayloads().setProperty("expId2", (id2));
		this.sendMessage(ae, req);
	}

}
