/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;

/**
 * @author wu
 * 
 */
public class SendAP extends ActionHandlerSupport {

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();
		((GChatControlI) c).send();

	}

}
