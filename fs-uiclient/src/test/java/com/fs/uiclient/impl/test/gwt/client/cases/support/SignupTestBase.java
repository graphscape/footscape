/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.signup.SignupModel;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wuzhen
 * 
 */
public abstract class SignupTestBase extends TestBase {

	protected SignupView signupView;

	protected String email;

	protected String password;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

	}

	public void start() {
		Mvc mvc = this.mcontrol.getLazyObject(MainControlI.LZ_SIGNUP, true);
	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();
		if (obj instanceof SignupView) {
			this.onSignupView((SignupView) obj);
		}
	}

	/**
	 * @param obj
	 */
	private void onSignupView(SignupView obj) {
		this.signupView = obj;//
		this.email = "user1@some.com";
		this.password = "pass1";
		FormView fv = obj.find(FormView.class, "default", true);

		EditorI unameE = fv.find(EditorI.class, "nick", true);
		unameE.input(StringData.valueOf("user1"));
		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input(StringData.valueOf(this.password));
		EditorI password2E = fv.find(EditorI.class, "password2", true);
		password2E.input(StringData.valueOf(this.password));
		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input(StringData.valueOf(this.email));
		EditorI isaE = fv.find(EditorI.class, "isAgree", true);

		isaE.input(BooleanData.valueOf(true));
		EditorI ccnE = fv.find(EditorI.class, "confirmCodeNotifier", true);
		ccnE.input(StringData.valueOf("resp"));

		obj.clickAction("submit");
	}

	/**
	 * @param c
	 * @param action
	 */
	protected void onSuccessResposne(String path, UiResponse sre) {

		if (path.endsWith("signup/" + SignupModel.A_SUBMIT)) {
			String ccode = sre.getPayLoadAsString("confirmCode", true);//

			FormView fv = this.signupView.find(FormView.class, "confirm", true);

			EditorI emailE = fv.find(EditorI.class, "email", true);
			emailE.input(StringData.valueOf("user1@some.com"));
			EditorI ccodeE = fv.find(EditorI.class, "confirmCode", true);// see
																			// ResponseConfirmCodeNodifier
			ccodeE.input(StringData.valueOf(ccode));

			this.signupView.clickAction(SignupModel.A_CONFIRM);
			this.tryFinish("submit");
		} else if (path.endsWith("signup/" + SignupModel.A_CONFIRM)) {

			this.tryFinish("confirm");
		}
	}

	protected abstract void onSignup(String email, String pass);

}
