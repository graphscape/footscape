/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 7, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wu
 * 
 */
public class UserNickView extends LightWeightView {

	private String accId;

	private String nick;

	private AnchorWI ar;

	/**
	 * @param c
	 * @param ele
	 */
	public UserNickView(ContainerI c, String accId, String nick) {
		super(c);
		this.accId = accId;
		this.nick = nick;
		ar = this.factory.create(AnchorWI.class);
		ar.getElement().addClassName("user-icon");
		ar.setDisplayText(nick);
		ar.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent t) {
				UserNickView.this.onUserInfoClick();
			}
		});
		ar.parent(this);
	}

	protected void onUserInfoClick() {
		this.dispatchActionEvent(Actions.A_EXPS_GETUSERINFO);
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		//
		super.beforeActionEvent(ae);
		ae.setProperty("accountId", this.accId);// for action:get
												// userInfo
	}

}
