/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *         <p>
 * 
 *         <p>
 * 
 */
public class RefreshIncomingCrAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = (ControlI)ae.getSource();
		CooperModelI cm = c.getModel();
		List<String> crIdL = null;
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/incomingCr"));

		req.getPayloads().setProperty("cooperRequestIdList", (crIdL));

		this.sendMessage(ae, req);
	}

}
