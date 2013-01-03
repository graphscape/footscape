/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public abstract class ActionHandlerSupport implements EventHandlerI<ActionEvent> {

	public ActionHandlerSupport() {
	}

	
	protected EndPointI getEndpoint(ActionEvent ae){
		return ae.getControl().getClient(true).getEndpoint();
	}
	
	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(ActionEvent ae, MsgWrapper req) {
		ControlI c = ae.getControl();
		c.getClient(true).getEndpoint().sendMessage(req);//
	}

}
