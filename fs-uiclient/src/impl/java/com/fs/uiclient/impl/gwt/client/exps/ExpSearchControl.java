/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.support.ControlSupport2;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ExpSearchControl extends ControlSupport2 implements ExpSearchControlI {

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
	public void search(UserExpModel ue, boolean showView) {
		ExpSearchViewI es = this.getMainControl().openExpSearch(showView);
		es.setExpId(ue);//

		new ActionEvent(this, Actions.A_EXPS_SEARCH).dispatch();
	}

	protected ExpSearchViewI getView() {
		return this.getMainControl().openExpSearch(false);
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public void addOrUpdateExpItem(ExpItemModel ei) {

		ExpSearchViewI esv = this.getMainControl().openExpSearch(false);
		esv.addExpItem(ei);
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public void reset() {
		ExpSearchViewI v = this.getMainControl().openExpSearch(false);
		v.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI#getExpId(boolean)
	 */
	@Override
	public UserExpModel getExpId(boolean b) {
		// TODO Auto-generated method stub
		return this.getView().getExpId(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI#getPhrase(boolean)
	 */
	@Override
	public String getPhrase(boolean b) {
		// TODO Auto-generated method stub
		return this.getView().getPhrase(b);
	}

}
