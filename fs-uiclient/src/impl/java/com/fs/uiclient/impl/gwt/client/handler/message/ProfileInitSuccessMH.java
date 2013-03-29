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
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ProfileInitSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public ProfileInitSuccessMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();

		MainControlI mc = this.getControl(MainControlI.class, true);

		ObjectPropertiesData opd = (ObjectPropertiesData) res.getPayload("profile");
		String icon = null;
		Integer age = 0;
		String gender = null;
		if (opd != null) {

			age = (Integer) opd.getProperty("age", true);

			gender = opd.getString("gender", true);
			icon = opd.getString("icon", true);

		}
		ProfileViewI pv = mc.openProfile();

		// pv.setEmail(email);
		pv.setIcon(icon);
		pv.setGender(gender);
		pv.setAge(age);

	}

}
