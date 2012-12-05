/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ActivityView extends SimpleView implements ManagableI {

	private ManagedModelI managed;

	/**
	 * @param ctn
	 */
	public ActivityView(String name, ContainerI ctn) {
		super(name, ctn);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public String getManager() {
		//
		return BossModelI.M_CENTER;// TODO FRONT
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

}
