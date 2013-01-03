/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class SimpleRequestAP extends ActionHandlerSupport {

	private Path path;

	public SimpleRequestAP(String path) {
		this.path = Path.valueOf(path);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		MsgWrapper req = this.newRequest(path);

		this.sendMessage(ae, req);
	}

}
