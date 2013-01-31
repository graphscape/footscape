/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ExpSearchControl extends ControlSupport implements ExpSearchControlI {

	/**
	 * @param name
	 */
	public ExpSearchControl(ContainerI c, String name) {
		super(c, name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI#search(java.lang
	 * .String)
	 */
	@Override
	public void search(String expId) {
		ExpSearchModelI es = this.getRootModel().find(ExpSearchModelI.class, true);
		es.setExpId(expId);//

		new ActionEvent(this, Actions.A_EXPS_SEARCH).dispatch();
	}

}
