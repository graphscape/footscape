/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.UiCommonsConstants;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.frwk.BottomViewI;
import com.fs.uicommons.api.gwt.client.frwk.ConsoleViewI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkViewI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.impl.gwt.client.frwk.console.ConsoleView;
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
		FrwkViewI fv = root.getChild(FrwkViewI.class, false);
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
	public LoginViewI openLoginView(boolean show) {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		return lc.openLoginView(show);

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public HeaderViewI getHeaderView() {
		//
		return this.getFrwkView().getHeader();
	}

	@Override
	public BottomViewI getBottomView() {
		//
		return this.getFrwkView().getBottom();
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void addHeaderItemIfNotExist(Path path) {
		//
		this.getHeaderView().addItemIfNotExist(path);
	}

	@Override
	public void tryRemoveHeaderItem(Path path) {
		this.getHeaderView().tryRemoveItem(path);
	}

	@Override
	public ConsoleViewI openConsoleView(boolean show) {
		BodyViewI bv = this.getFrwkView().getBodyView();
		ConsoleViewI rt = bv.getOrCreateItem(UiCommonsConstants.P_CONSOLE_VIEW,
				new com.fs.uicommons.api.gwt.client.CreaterI<ConsoleViewI>() {

					@Override
					public ConsoleViewI create(ContainerI ct) {

						return new ConsoleView(ct);
					}
				}, show);
		return rt;
	}
}
