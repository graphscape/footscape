/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ActivityView extends SimpleView {

	protected ListI list;

	/**
	 * @param ctn
	 */
	public ActivityView(String id, ContainerI ctn, ActivityModelI am) {
		super(Actions.A_ACT, id, ctn, am);
		this.list = this.factory.create(ListI.class);
		this.list.setName("partnerList");
		this.list.parent(this);
		for (PartnerModel pm : am.getParticipantList()) {
			this.addPartner(pm);
		}
	}

	public ActivityModelI getActivityModel() {
		return (ActivityModelI) this.model;
	}

	public String getActId() {
		return this.getActivityModel().getActivityId();
	}

	/**
	 * Jan 15, 2013
	 */
	public void addPartner(PartnerModel cm) {
		String expId = cm.getExpId();
		String accId = cm.getAccountId();
		LabelI lb = this.factory.create(LabelI.class);
		lb.getModel().setDefaultValue("expId:" + expId + ",accId:" + accId);
		lb.parent(this.list);
	}

	/*
	 * Feb 3, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		//
		super.beforeActionEvent(ae);
		ae.setProperty("actId", this.getActId());
	}

}
