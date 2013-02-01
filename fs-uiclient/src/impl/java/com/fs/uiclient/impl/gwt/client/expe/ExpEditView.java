/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ExpEditView extends FormsView implements ViewReferenceI.AwareI {

	private ViewReferenceI managed;

	/**
	 * @param ctn
	 */
	public ExpEditView(String name, ContainerI ctn,ExpEditModel em) {
		super(name, ctn,em);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ViewReferenceI.AwareI
	 * #
	 * setManaged(com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI
	 * .ViewReferenceI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

}
