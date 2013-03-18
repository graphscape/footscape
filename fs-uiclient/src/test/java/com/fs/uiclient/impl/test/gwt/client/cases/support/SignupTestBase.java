/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
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
public abstract class SignupTestBase extends TestBase {

	protected SignupView signupView;

	protected String email;

	protected String password;

	@Override
	public void onEvent(Event e) {
		//
		super.onEvent(e);

	}

	@Override
	protected void onBondEvent(EndpointBondEvent e) {
		UserInfo ui = e.getChannel().getUserInfo();
		if (ui.isAnonymous()) {//
			// open signup view
			this.mcontrol.openSignup();
		} else {

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

			this.tryFinish("submit");
			this.onSignup(this.email, this.password);
			this.tryFinish("confirm");
		}
	}


	/**
	 * @param obj
	 */
	private void onSignupView(SignupView obj) {
		this.signupView = obj;//
		this.email = "user1@some.com";
		this.password = "pass1";

		FormView fv = this.signupView.find(FormView.class, "default", true);

		EditorI unameE = fv.find(EditorI.class, "nick", true);
		unameE.input(("user1"));

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
