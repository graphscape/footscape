/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class PasswordForgotSuccessMH extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public PasswordForgotSuccessMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		//
		MessageData res = t.getMessage();
		// notify user a message send to the email
		Window.alert("A email has sent to the you,please check it and following the instruction there!");
	}

}
