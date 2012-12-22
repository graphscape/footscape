/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.auth;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionFailedEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.impl.gwt.client.frwk.header.HeaderView;
import com.fs.uicommons.impl.gwt.client.frwk.header.ItemView;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginView;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelValueHandler;

/**
 * @author wu
 * 
 */
public class LoginTest extends TestBase {

	private LoginView loginView;

	private LoginControlI loginControl;

	private SessionModelI sessionModel;

	private LoginModelI loginModel;

	private WidgetI loginManaged;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		this.finishing.add("loginrequired");

		this.finishing.add("afterSubmit");

		this.finishing.add("loginSuccess");//

	}

	public void testLogin() {

		this.dump();

		this.loginView = this.root.find(LoginView.class, true);
		this.loginControl = this.manager.getControl(LoginControlI.class, true);

		this.loginControl.addActionEventHandler("submit",
				new HandlerI<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						// DO nothing
					}
				});

		loginModel = this.rootModel.getChild(LoginModelI.class, true);

		assertEquals("login model is not cerrect", loginModel,
				this.loginView.getModel());

		assertEquals("login model is not cerrect", loginModel,
				this.loginControl.getModel());

		this.sessionModel = this.loginModel.getSessionModel();
		assertNotNull("session model is null.", this.sessionModel);
		assertFalse("init time should not login required.",
				sessionModel.isLoginRequired());

		// when click the header item,the view should be open
		sessionModel.addValueHandler(SessionModelI.L_LOGIN_REQUIRED,
				Boolean.TRUE, new ModelValueHandler<Boolean>(Boolean.FALSE) {

					@Override
					public void handleValue(Boolean value) {
						LoginTest.this.onLoginRequired();
					}
				});
		//
		sessionModel.addValueHandler(SessionModelI.L_IS_AUTHED, Boolean.TRUE,
				new ModelValueHandler<Boolean>(Boolean.FALSE) {

					@Override
					public void handleValue(Boolean value) {
						LoginTest.this.onLoginSuccess();
					}
				});

		this.loginControl.addActionEventHandler(ActionFailedEvent.TYPE,
				"submit", new HandlerI<ActionFailedEvent>() {

					@Override
					public void handle(ActionFailedEvent e) {
						LoginTest.this.onLoginError(e);
					}
				});

		ActionModelI submitA = ControlUtil.getAction(loginModel, "submit");
		submitA.addProcessedHandler(new HandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				LoginTest.this.onSubmitAfter(e);
			}
		});
		assertTrue("session view should attached.", this.loginView.isAttached());

		this.loginManaged = (WidgetI) this.loginView.getParent();

		assertTrue(
				"login view's managed widget should not visible,it is in the center view",
				!this.loginManaged.isVisible());

		this.delayTestFinish(timeoutMillis * 100);

		final HeaderModelI hm = this.rootModel.getChild(HeaderModelI.class, true);
		
		ItemModel imLogin = hm.getItem("login", true);

		ItemView iv = (ItemView) imLogin.getValue(ModelI.L_LAST_WIDGET);
		if (iv == null) {

			imLogin.addValueHandler(ModelI.L_LAST_WIDGET,
					new HandlerI<ModelValueEvent>() {

						@Override
						public void handle(ModelValueEvent e) {
							//
							ItemView view = (ItemView) e.getValue();

							LoginTest.this.onHeaderModelWidget(view);
						}
					});
		} else {
			this.onHeaderModelWidget(iv);
		}

	}

	protected void onHeaderModelWidget(ItemView iv) { //

		HeaderView hv = this.root.find(HeaderView.class, false);
		hv.dump();
		assertNotNull("header view not found,", hv);

		AnchorWI headerItemW = hv.find(AnchorWI.class, "header-item-"
				+ LoginView.HEADER_ITEM_LOGIN, true);

		headerItemW.click();// open session view,this will cause
							// SessionModelI.isLoginRequired() return true.

	}

	/**
	 * @param e
	 */
	protected void onSubmitAfter(ModelValueEvent e) {
		// check login success
		//

		this.tryFinish("afterSubmit");
	}

	protected void onLoginError(ActionFailedEvent e) {

		assertTrue("Login error:" + e.getErrorInfosData(), false);
		this.finishTest();

	}

	protected void onLoginSuccess() {

		String account = this.sessionModel.getAccount();

		assertNotNull("account is null", account);
		
		this.tryFinish("loginSuccess");
	}

	/**
	 * 
	 */
	protected void onLoginRequired() {
		assertTrue("session view should be open", this.loginView.isVisible());
		StringEditorI unameE = this.loginView.find(StringEditorI.class,
				"username", true);
		unameE.input(StringData.valueOf("user1"));

		StringEditorI passwordE = this.loginView.find(StringEditorI.class,
				"password", true);
		unameE.input(StringData.valueOf("pass1"));

		this.loginView.clickAction("submit");// submit
		LoginTest.this.tryFinish("loginrequired");

	}

}
