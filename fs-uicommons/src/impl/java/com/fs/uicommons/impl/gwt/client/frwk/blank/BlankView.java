/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.blank;

import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class BlankView extends SimpleView implements ManagableI {

	private ManagedModelI managed;

	/**
	 * @param ctn
	 */
	public BlankView(ContainerI ctn) {
		super(ctn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagableI#setManaged
	 * (com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedModelI)
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

	/* (non-Javadoc)
	 * @see com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ManagableI#getManager()
	 */
	@Override
	public String getManager() {
		// TODO Auto-generated method stub
		return BossModelI.M_CENTER;
	}

}
