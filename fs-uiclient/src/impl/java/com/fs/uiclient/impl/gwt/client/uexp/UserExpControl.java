/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.uexp.UserExpControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.handler.action.UserExpCooperConfirmAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class UserExpControl extends ControlSupport implements UserExpControlI {

	/**
	 * @param name
	 */
	public UserExpControl(String name) {
		super(name);
		this.addActionEventHandler(UserExpModel.A_OPEN_ACTIVITY, new OpenActivityAP());
		this.addActionEventHandler(UserExpModel.A_SELECT, new SelectAP());
		this.addActionEventHandler(UserExpModel.A_COOPER_CONFIRM, new UserExpCooperConfirmAP());
	}

	@Override
	protected void doModel(ModelI cm) {
		super.doModel(cm);

	}

}
