/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.google.gwt.user.client.Window;

/**
 * @author wuzhen
 * 
 */
public abstract class SignupTestWorker extends AbstractTestWorker {

	protected SignupViewI signupView;

	protected String email;

	protected String password;

	protected String nick;

	private boolean signingUp = false;

	public SignupTestWorker(String nick, String email, String pass) {
		this.nick = nick;
		this.email = email;
		this.password = pass;
		this.tasks.add("signup.done");
	}

	public void onEvent(Event e) {
		//
		super.onEvent(e);

	}

	@Override
	public void start(UiClientI client) {
		super.start(client);
		this.doStart();
	}

	protected void doStart() {
		UserInfo ui = this.getUserInfo(true);
		if (ui == null) {
			Window.alert("anonymous user info is null");
			return;
		}

		if (!ui.isAnonymous()) {//
			throw new UiException("user info not anonymous,ui:" + ui);
		}

		this.signupView = this.mcontrol.openSignup();
		if (this.signupView.isAttached()) {
			this.trySignup();
		}
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	protected void onAttachedEvent(AttachedEvent ae) {
		//
		super.onAttachedEvent(ae);
		UiObjectI src = ae.getSource();
		if (src instanceof SignupView) {
			this.trySignup();
		}
	}

	@Override
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		MessageData res = e.getMessage();
		Path p = e.getMessage().getPath().getParent();
		if (p.equals(Path.valueOf("/endpoint/message/signup/submit"))) {

			this.tryFinish("signup.done");
			this.onSignup(this.email, this.password);
		}
	}

	/**
	 * @param obj
	 */
	private void trySignup() {
		if (this.signingUp) {
			return;
		}

		this.signingUp = true;

		FormView fv = this.signupView.find(FormView.class, "default", true);

		EditorI unameE = fv.find(EditorI.class, "nick", true);
		unameE.input(this.nick);

		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input((this.password));

		// EditorI password2E = fv.find(EditorI.class, "password2", true);
		// password2E.input((this.password));

		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input((this.email));

		// EditorI isaE = fv.find(EditorI.class, "isAgree", true);
		// isaE.input((true));

		// EditorI ccnE = fv.find(EditorI.class, "confirmCodeNotifier", true);
		// ccnE.input(("resp"));

		this.signupView.clickAction(Actions.A_SIGNUP_SUBMIT);

	}

	protected abstract void onSignup(String email, String pass);

}
