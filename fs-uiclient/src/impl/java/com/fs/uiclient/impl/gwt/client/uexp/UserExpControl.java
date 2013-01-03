/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.uexp.UserExpControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;

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
		this.localMap.put(UserExpModel.A_OPEN_ACTIVITY, true);//
		this.addActionEventHandler(UserExpModel.A_OPEN_ACTIVITY,
				new OpenActivityAP());
		this.localMap.put(UserExpModel.A_SELECT, true);//
		this.addActionEventHandler(UserExpModel.A_SELECT,
				new SelectAP());
		
	}

}
