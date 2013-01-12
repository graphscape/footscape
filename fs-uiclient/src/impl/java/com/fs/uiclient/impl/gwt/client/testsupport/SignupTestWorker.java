/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public abstract class SignupTestWorker extends TestWorker {

	protected SignupView signupView;

	protected String email;

	protected String password;
	
	protected String nick;
	
	public SignupTestWorker(String nick, String email, String pass){
		this.nick = nick;
		this.email = email;
		this.password = pass;
	}

	public void onEvent(Event e) {
		//
		super.onEvent(e);

	}

	@Override
	protected void onBondEvent(EndpointBondEvent e) {
		UserInfo ui = e.getChannel().getUserInfo();
		if (ui.isAnonymous()) {//
			// open signup view
			Mvc mvc = this.mcontrol.getLazyObject(MainControlI.LZ_SIGNUP, true);

		}
	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();
		if (obj instanceof SignupView) {
			this.onSignupView((SignupView) obj);
		}
	}

	@Override
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		MessageData res = e.getMessage();
		Path p = e.getMessage().getPath().getParent();
		if (p.equals(Path.valueOf("/endpoint/message/signup/submit"))) {
			String ccode = res.getString("confirmCode", true);//

			FormView fv = this.signupView.find(FormView.class, "confirm", true);

			EditorI emailE = fv.find(EditorI.class, "email", true);
			emailE.input((this.email));
			EditorI ccodeE = fv.find(EditorI.class, "confirmCode", true);// see
																			// ResponseConfirmCodeNodifier
			ccodeE.input((ccode));

			this.signupView.clickAction(Actions.A_SIGNUP_CONFIRM);
		} else if (p.equals(Path.valueOf("/endpoint/message/signup/confirm"))) {
			this.onSignup(this.email, this.password);
		}
	}


	/**
	 * @param obj
	 */
	private void onSignupView(SignupView obj) {
		this.signupView = obj;//

		FormView fv = this.signupView.find(FormView.class, "default", true);

		EditorI unameE = fv.find(EditorI.class, "nick", true);
		unameE.input(this.nick);

		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input((this.password));

		EditorI password2E = fv.find(EditorI.class, "password2", true);
		password2E.input((this.password));

		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input((this.email));

		EditorI isaE = fv.find(EditorI.class, "isAgree", true);
		isaE.input((true));

		EditorI ccnE = fv.find(EditorI.class, "confirmCodeNotifier", true);
		ccnE.input(("resp"));

		this.signupView.clickAction(Actions.A_SIGNUP_SUBMIT);

	}
	protected abstract void onSignup(String email, String pass);

}
