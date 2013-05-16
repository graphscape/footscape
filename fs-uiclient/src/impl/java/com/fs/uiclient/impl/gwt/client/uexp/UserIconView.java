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
import com.fs.uicore.api.gwt.client.commons.ImageUrl;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wu
 * 
 */
public class UserIconView extends LightWeightView {

	private String accId;

	private ImageUrl icon;
	private AnchorWI ar;

	/**
	 * @param c
	 * @param ele
	 */
	public UserIconView(ContainerI c, String accId, ImageUrl icon) {
		super(c);
		this.accId = accId;
		this.icon = icon;
		ar = this.factory.create(AnchorWI.class);
		ar.getElement().addClassName("user-icon");
		ar.setImage(icon.getAsSrc(this.getClient(true)));//
		
		ar.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent t) {
				UserIconView.this.onUserInfoClick();
			}
		});
		ar.parent(this);
	}

	protected void onUserInfoClick() {
		this.dispatchActionEvent(Actions.A_EXPS_GETUSERINFO);
	}

	public void setNick(String nick) {

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
