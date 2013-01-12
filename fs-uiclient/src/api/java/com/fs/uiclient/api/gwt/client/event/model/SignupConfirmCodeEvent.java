/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.api.gwt.client.event.model;

import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uicore.api.gwt.client.event.ModelUpdateEvent;

/**
 * @author wuzhen
 *         <p>
 * @see SignupModelI
 * 
 */
public class SignupConfirmCodeEvent extends ModelUpdateEvent {
	public static final Type<SignupConfirmCodeEvent> TYPE = new Type<SignupConfirmCodeEvent>(
			ModelUpdateEvent.TYPE, "signup-confirm-code");

	/**
	 * @param m
	 * @param loc
	 * @param vw
	 */
	public SignupConfirmCodeEvent(SignupModelI m, String code) {
		super(TYPE, m, "confirmCode", code);
	}

	public String getConfirmCode() {
		return this.getValue();
	}

	public SignupModelI getModel() {
		return (SignupModelI) this.source;
	}

}
