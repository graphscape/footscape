/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.api.gwt.client.event.model;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicore.api.gwt.client.event.ModelUpdateEvent;

/**
 * @author wuzhen
 *         <p>
 *         This event is raised on user exp model, when it have received one
 *         incoming cr(cooper request).
 * @see UserExpModel
 * 
 */
public class UserExpActivityEvent extends ModelUpdateEvent {
	public static final Type<UserExpActivityEvent> TYPE = new Type<UserExpActivityEvent>(
			ModelUpdateEvent.TYPE, "unkown");

	/**
	 * @param m
	 * @param loc
	 * @param vw
	 */
	public UserExpActivityEvent(UserExpModel m, String actId) {
		super(TYPE, m, "activityId", actId);
	}

	public UserExpModel getModel() {
		return (UserExpModel) this.source;
	}

}
