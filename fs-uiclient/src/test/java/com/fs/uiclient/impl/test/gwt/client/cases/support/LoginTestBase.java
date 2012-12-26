/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginModel;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginView;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wuzhen
 * 
 */
public abstract class LoginTestBase extends SignupTestBase {

	protected LoginView loginView;

	@Override
	public void start() {
		super.start();
	}

	@Override
	protected void onSignup(String email, String pass) {
		LoginControlI lc = this.manager.getControl(LoginControlI.class, true);
		FormView fv = this.loginView.find(FormView.class, "default", true);

		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input(StringData.valueOf(this.password));
		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input(StringData.valueOf(this.email));

		this.loginView.clickAction("submit");
	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof AfterAuthEvent) {
			this.onAfterAuthEvent((AfterAuthEvent) e);
		}
	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();
		if (obj instanceof LoginView) {
			this.onLoginView((LoginView) obj);
		}
	}

	/**
	 * @param obj
	 */
	protected void onLoginView(LoginView obj) {
		this.loginView = obj;
	}

	protected void onSuccessResposne(String path, UiResponse sre) {
		super.onSuccessResposne(path, sre);
		if (path.endsWith("login/" + LoginModel.A_SUBMIT)) {
			String sid = sre.getPayLoadAsString("sessionId", true);//

		}
	}
	
	protected void onAfterAuthEvent(AfterAuthEvent e) {

	}
	
	/**
	 * 
	 */

}
