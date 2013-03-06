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
 * @see UserExpItemView
 */
public class UserExpCrConfirmEvent extends ModelUpdateEvent {
	public static final Type<UserExpCrConfirmEvent> TYPE = new Type<UserExpCrConfirmEvent>(
			ModelUpdateEvent.TYPE, "user-exp-cr-confirm");

	/**
	 * @param m
	 * @param loc
	 * @param vw
	 */
	public UserExpCrConfirmEvent(UserExpModel m, String crId) {
		super(TYPE, m, "crId", crId);
	}

	public UserExpModel getModel() {
		return (UserExpModel) this.source;
	}

}
