/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.api.gwt.client.event.model;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicore.api.gwt.client.event.ModelUpdateEvent;

/**
 * @author wuzhen
 * 
 */
public class UserExpSelectEvent extends ModelUpdateEvent {
	public static final Type<UserExpSelectEvent> TYPE = new Type<UserExpSelectEvent>(
			ModelUpdateEvent.TYPE, "unkown");

	/**
	 * @param m
	 * @param loc
	 * @param vw
	 */
	public UserExpSelectEvent(UserExpModel m, boolean sel) {
		super(TYPE, m, "select", sel);
	}
	
	public UserExpModel getModel(){
		return (UserExpModel)this.source;
	}
	

}
