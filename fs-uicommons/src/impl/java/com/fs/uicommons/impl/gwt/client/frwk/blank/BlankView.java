/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.blank;

import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class BlankView extends SimpleView implements ViewReferenceI.AwareI {

	private ViewReferenceI managed;

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
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ViewReferenceI.AwareI#setManaged
	 * (com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ViewReferenceI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

}
