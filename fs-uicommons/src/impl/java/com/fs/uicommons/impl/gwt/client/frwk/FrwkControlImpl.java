/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.Position;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
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

	public static final CreaterI<FrwkModelI> CREATER = new CreaterI<FrwkModelI>() {

		@Override
		public FrwkModelI create(ContainerI c) {
			return new FrwkModelImpl("frwk");
		}
	};

	/**
	 * @param c
	 * @param name
	 */
	public FrwkControlImpl(ContainerI c, String name) {
		super(c, name);

	}

	@Override
	public void open() {
		FrwkModelI fm = this.getOrCreateModel(this.getRootModel(), FrwkModelI.class, CREATER);
		RootI root = this.getClient(true).getRoot();
		FrwkView fv = root.getChild(FrwkView.class, false);
		if (fv == null) {
			fv = new FrwkView("frwk", this.container, fm);
			fv.parent(root);
		}
	}

	public FrwkModelI getFrwkModel() {
		return this.getRootModel().find(FrwkModelI.class, true);
	}

	@Override
	public void addHeaderItem(Path path) {

		this.getFrwkModel().getHeader().addItem(path, Position.valueOf("right"));

		this.getFrwkView().getHeader().addItem(path);

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
}
