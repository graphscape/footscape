/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.user.UserInfo;
import com.fs.uiclient.api.gwt.client.user.UserInfoViewI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class UserInfoSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public UserInfoSuccessMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		PropertiesData<Object> pts = (PropertiesData<Object>)res.getPayload("userInfo",true);
		
		UserInfo ui = new UserInfo(pts);
		MainControlI mc = this.getControl(MainControlI.class, true);
		String accId = ui.getAccountId();
		
		UserInfoViewI uiv = mc.openUserInfo(false);
		uiv.setUserInfo(ui);
	}

}
