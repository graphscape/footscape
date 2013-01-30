/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class ActivityView extends SimpleView implements ViewReferenceI.AwareI {

	private ViewReferenceI managed;
	
	protected ListI list;

	/**
	 * @param ctn
	 */
	public ActivityView(String name, ContainerI ctn) {
		super(name, ctn);
		this.list = this.factory.create(ListI.class);
		this.list.setName("partnerList");
		this.list.parent(this);
	}
	
	@Override
	public void doModel(ModelI model) {
		super.doModel(model);
	
	}

	/**
	 *Jan 15, 2013
	 */
	private void processChildPartnerModelAdd(PartnerModel cm) {
		String expId = cm.getExpId();
		String accId = cm.getAccountId();
		LabelI lb = this.factory.create(LabelI.class);
		lb.getModel().setDefaultValue("expId:"+expId+",accId:"+accId);
		lb.parent(this.list);
	}
	
	/*
	 * Oct 20, 2012
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

	/*
	 *Jan 15, 2013
	 */
	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		// 
		super.processChildModelAdd(parent, cm);
		if(cm instanceof PartnerModel){
			this.processChildPartnerModelAdd((PartnerModel)cm);
		}
	}


}
