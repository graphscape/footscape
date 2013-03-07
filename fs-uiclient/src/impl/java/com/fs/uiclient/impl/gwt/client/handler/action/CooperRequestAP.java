/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *         <p>
 *         Send request for cooperation with another exp.
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
		String id1 = (String) ae.getProperty("expId1", true);
		String id2 = (String) ae.getProperty("expId2", true);

		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/request"));
		req.getPayloads().setProperty("expId1", (id1));
		req.getPayloads().setProperty("expId2", (id2));
		this.sendMessage(req);
	}

}
