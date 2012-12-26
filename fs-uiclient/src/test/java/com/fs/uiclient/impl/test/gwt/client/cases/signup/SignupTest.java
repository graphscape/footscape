/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import org.junit.Before;
import org.junit.Test;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.signup.SignupControl;
import com.fs.uiclient.impl.gwt.client.signup.SignupModel;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionSuccessEvent;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.ErrorResponseEvent;

/**
 * @author wuzhen
 * 
 */
public class SignupTest extends TestBase {

	protected SignupView signupView;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		this.finishing.add("submit");
		this.finishing.add("confirm");
	}

	@Test
	public void testDefaultCase() {

		Mvc mvc = this.mcontrol.getLazyObject(MainControlI.LZ_SIGNUP, true);

		this.delayTestFinish(1000 * 1000);
		//

	}

	public void start() {

	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof AttachedEvent) {
			AttachedEvent ae = (AttachedEvent) e;
			UiObjectI obj = ae.getSource();
			if (obj instanceof SignupView) {
				this.onSignupView((SignupView) obj);
			}
		} else if (e instanceof ActionSuccessEvent) {
			ActionSuccessEvent ase = (ActionSuccessEvent) e;
			ControlI c = ase.getControl();
			String action = ase.getAction();
			if (c instanceof SignupControl) {
				this.onSignupActionSuccess((SignupControl) c, action);
			}
		} 
	}

	/**
	 * @param c
	 * @param action
	 */
	private void onSignupActionSuccess(SignupControl c, String action) {
		if (action.equals(SignupModel.A_SUBMIT)) {

			FormView fv = this.signupView.find(FormView.class, "confirm", true);

			EditorI emailE = fv.find(EditorI.class, "email", true);
			emailE.input(StringData.valueOf("user1@some.com"));
			EditorI ccodeE = fv.find(EditorI.class, "confirmCode", true);
			ccodeE.input(StringData.valueOf("confirm-001"));

			this.signupView.clickAction(SignupModel.A_CONFIRM);
			this.tryFinish("submit");
		} else if (action.equals(SignupModel.A_CONFIRM)) {
			this.tryFinish("confirm");
		}
	}

	/**
	 * @param obj
	 */
	private void onSignupView(SignupView obj) {
		this.signupView = obj;//
		FormView fv = obj.find(FormView.class, "default", true);

		EditorI unameE = fv.find(EditorI.class, "nick", true);
		unameE.input(StringData.valueOf("user1"));
		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input(StringData.valueOf("pass1"));
		EditorI password2E = fv.find(EditorI.class, "password2", true);
		password2E.input(StringData.valueOf("pass1"));
		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input(StringData.valueOf("user1@some.com"));
		EditorI isaE = fv.find(EditorI.class, "isAgree", true);
		isaE.input(BooleanData.valueOf(true));
		obj.clickAction("submit");
	}

}
