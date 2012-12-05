/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wuzhen
 * 
 */
public class AttacherSupport implements UiObjectI.AttacherI {

	protected UiObjectI owner;

	@Override
	public void owner(UiObjectI obj) {
		this.owner = obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.AttacherI#getOwner(boolean)
	 */
	@Override
	public UiObjectI getOwner(boolean force) {
		if (this.owner == null && force) {
			throw new UiException("no owner for:" + this);
		}
		return this.owner;

	}

	@Override
	public void ownerAttached() {
		this.doAttach();
	}

	protected void doAttach() {

	}

	protected void doDettach() {

	}

	@Override
	public void ownerDettached() {
		this.doDettach();
	}

}
