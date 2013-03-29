/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.profile.ProfileViewI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class ProfileSubmitSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public ProfileSubmitSuccessMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData req = t.getMessage();
		MainControlI mc = this.getControl(MainControlI.class, true);
		ProfileViewI pv = mc.openProfile();
		Window.alert("Profile saved Successfully!");
	}

}
