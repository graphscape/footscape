/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import org.junit.Before;
import org.junit.Test;

import com.fs.uiclient.impl.gwt.client.signup.SignupControl;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionSuccessEvent;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wuzhen
 * 
 */
public class SignupTest extends TestBase {

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		this.finishing.add("submit");
		this.finishing.add("confirm");
	}

	@Test
	public void testDefaultCase() {

		SignupControl mc = this.manager.getChild(SignupControl.class, true);
		mc.addActionEventHandler(ActionSuccessEvent.TYPE, "submit",
				new EventHandlerI<ActionSuccessEvent>() {

					@Override
					public void handle(ActionSuccessEvent e) {
						SignupTest.this.onSubmitActionSuccessEvent(e);
					}
				});
		mc.addActionEventHandler(ActionSuccessEvent.TYPE, "confirm",
				new EventHandlerI<ActionSuccessEvent>() {

					@Override
					public void handle(ActionSuccessEvent e) {
						SignupTest.this.onConfirmActionSuccessEvent(e);
					}
				});
		
		FormsView fsv = this.root.find(FormsView.class, "signup", true);
		FormView fv = fsv.find(FormView.class, "default", true);

		EditorI unameE = fv.find(EditorI.class, "username", true);
		unameE.input(StringData.valueOf("user1"));
		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input(StringData.valueOf("pass1"));
		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input(StringData.valueOf("user1@some.com"));
		EditorI isaE = fv.find(EditorI.class, "isAgree", true);
		isaE.input(BooleanData.valueOf(true));

		this.delayTestFinish(10 * 1000);
		//
		fsv.clickAction("submit");

	}

	/**
	 * @param e
	 */
	protected void onSubmitActionSuccessEvent(ActionSuccessEvent e) {
		System.out.println("e:" + e);
		
		FormsView fsv = this.root.find(FormsView.class, "signup", true);
		FormView fv = fsv.find(FormView.class, "confirm", true);

		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input(StringData.valueOf("user1@some.com"));
		EditorI ccodeE = fv.find(EditorI.class, "confirmCode", true);
		ccodeE.input(StringData.valueOf("confirm-001"));
		
		fsv.clickAction("confirm");
		this.tryFinish("submit");
		
	}

	protected void onConfirmActionSuccessEvent(ActionSuccessEvent e) {
		System.out.println("e:" + e);

		this.tryFinish("confirm");
	}

}
