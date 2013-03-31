/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class PasswordForgotFailureMH extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public PasswordForgotFailureMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		LoginViewI pv = lc.openLoginView();
		MessageData req = t.getMessage();
		ErrorInfosData eis = (ErrorInfosData) req.getPayload(UiResponse.ERROR_INFO_S);
		pv.addErrorInfo(eis);//
	}

}
