/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wuzhen
 * 
 */
public class FrwkControlImpl extends ControlSupport implements FrwkControlI {

	/**
	 * @param c
	 * @param name
	 */
	public FrwkControlImpl(ContainerI c, String name) {
		super(c, name);

	}

	@Override
	public void open() {
		RootI root = this.getClient(true).getRoot();
		FrwkView fv = root.getChild(FrwkView.class, false);
		if (fv == null) {
			fv = new FrwkView(this.container);
			fv.parent(root);
		}
	}

	@Override
	public void addHeaderItem(Path path) {
		this.addHeaderItem(path, false);
	}

	@Override
	public void addHeaderItem(Path path, boolean left) {
		this.getFrwkView().getHeader().addItem(path, left);

	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public LoginViewI openLoginView() {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		return lc.openLoginView();

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public HeaderViewI getHeaderView() {
		//
		return this.getFrwkView().getHeader();
	}

	/*
	 *Mar 30, 2013
	 */
	@Override
	public void addHeaderItemIfNotExist(Path path) {
		// 
		this.getHeaderView().addItemIfNotExist(path);
	}

	/*
	 *Mar 30, 2013
	 */
	@Override
	public void tryRemoveHeaderItem(Path path) {
		this.getHeaderView().tryRemoveItem(path);
	}
}
