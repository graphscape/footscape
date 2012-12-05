package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.simple.FormDataAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * auth action is not login action,it will do auth like the submit. login just
 * open the login form.
 * 
 * @author wu
 * 
 */
public class LoginControl extends ControlSupport implements LoginControlI {

	public LoginControl(String name) {
		super(name);

		// this.localMap.put(LoginModelI.A_LOGOUT, Boolean.TRUE);// local only
		// for
		// open view
		// TODO
		this.addActionProcessor(LoginModelI.A_AUTH, new AutoAuthAP());
		this.addActionProcessor(LoginModelI.A_SUBMIT, new FormDataAP());
		this.addActionProcessor(LoginModelI.A_SUBMIT, new SubmitAP());

	}

	@Override
	public ControlI model(ModelI cm) {
		super.model(cm);
		// listen to login required
		// this.getModel().addValueHandler(SessionModelI.L_LOGIN_REQUIRED, new
		// ModelValueHandler<Boolean>(){
		//
		// @Override
		// public void handleValue(Boolean value) {
		// if(value != null&&value){
		// //create a view or show the view.
		// SessionControl.this.showLogin();
		// }
		// }});
		return this;
	}

	@Override
	protected void doAttach() {
		super.doAttach();
		UiClientI c = this.getClient(true);
		// listen client start event,trigger auth action,
		// c.addHandler(ClientStartEvent.TYPE, new HandlerI<ClientStartEvent>()
		// {
		//
		// @Override
		// public void handle(ClientStartEvent e) {
		// SessionControl.this.getModel().getAction(SessionModelI.A_AUTH)
		// .trigger();
		// }
		// });
	}

	@Override
	public LoginModelI getModel() {
		return (LoginModelI) this.model;
	}

	@Override
	public void processRequest(ControlI c, String a) {
		super.processRequest(c, a);
		if (LoginModelI.A_LOGOUT.equals(a)) {
			this.getModel().getSessionModel().setLoginRequired(true);// show the
																		// login
																		// view.
		}
	}
	
	@Override
	protected void onActionSuccess(String action, UiResponse res) {
		super.onActionSuccess(action, res);
		

	}

	@Override
	protected void onActionFailed(String action, UiResponse res) {
		super.onActionFailed(action, res);

	}

	@Override
	public void addAuthActionProcessor(ActionProcessorI ap) {
		this.addActionProcessor(LoginModelI.A_AUTH, ap);
	}

}
