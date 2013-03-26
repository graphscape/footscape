/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.facebook.AuthLoginResponseJso;
import com.fs.uiclient.api.gwt.client.facebook.Facebook;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class SignupView extends FormsView implements SignupViewI {

	private static UiLoggerI LOG = UiLoggerFactory.getLogger(SignupView.class);

	public static String HEADER_ITEM_SIGNUP = "signup";//

	protected ItemModel headerSignupItem;

	private ViewReferenceI managed;

	/**
	 * @param ctn
	 */
	public SignupView(ContainerI ctn) {
		super(ctn, "signup");
		//
		this.addAction(Actions.A_SIGNUP_SUBMIT);

		// form1
		FormViewI def = this.getDefaultForm();
		// actions for form1
		def.getFormModel().addAction(Actions.A_SIGNUP_SUBMIT);

		// fields1
		def.addField("nick", String.class);
		def.addField("password", String.class);
		def.addField("email", String.class);

		// facebook login button
		Element fbb = Facebook.createLoginButtonDiv().cast();
		this.element.appendChild(fbb);
		// the init should be after this element is attached to root?
		Facebook fb = Facebook.getInstance();
		fb.onAuthLogin(new HandlerI<AuthLoginResponseJso>() {

			@Override
			public void handle(AuthLoginResponseJso t) {
				SignupView.this.onFacebookAuth(t);
			}
		});
		fb.start(true);
	}

	/**
	 *Mar 26, 2013
	 */
	protected void onFacebookAuth(AuthLoginResponseJso t) {
		
	}

	// show or hidden this view by model value
	protected void onSignupRequired(ModelValueEvent e) {
		boolean sr = e.getValue(Boolean.FALSE);
		this.managed.select(sr);//
	}

}
