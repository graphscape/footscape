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
 * @see UserExpView
 */
public class UserExpIncomingCrEvent extends ModelUpdateEvent {
	public static final Type<UserExpIncomingCrEvent> TYPE = new Type<UserExpIncomingCrEvent>(
			ModelUpdateEvent.TYPE, "user-exp-incoming-cr");

	/**
	 * @param m
	 * @param loc
	 * @param vw
	 */
	public UserExpIncomingCrEvent(UserExpModel m, String crId) {
		super(TYPE, m, "crId", crId);
	}

	public UserExpModel getModel() {
		return (UserExpModel) this.source;
	}

}
