/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.support.ActionHandlerSupport2;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *         <p>
 * 
 *         <p>
 * 
 */
public class RefreshIncomingCrAP extends ActionHandlerSupport2 {

	/**
	 * @param c
	 */
	public RefreshIncomingCrAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		CooperModelI cm = this.getMainControl().getCooperModel();
		List<String> crIdL = null;
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/incomingCr"));

		req.getPayloads().setProperty("cooperRequestIdList", (crIdL));

		this.sendMessage(req);
	}

}
