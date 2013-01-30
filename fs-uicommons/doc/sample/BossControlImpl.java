/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.BossControlI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ViewReferenceI.AwareI;
import com.fs.uicommons.api.gwt.client.manage.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.manage.ManagerModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public class BossControlImpl extends ControlSupport implements BossControlI {

	/**
	 * @param name
	 */
	public BossControlImpl(String name) {
		super(name);
	}

	@Override
	public ManagerModelI getManager(String name) {

		return this.model.getChild(ManagerModelI.class, name, true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.FrwkModelI#manage(com.fs.uicore.
	 * api.gwt.client.ModelI, com.fs.uicore.api.gwt.client.core.WidgetI)
	 */
	@Override
	public ViewReferenceI manage(ModelI model, WidgetI view) {
		String manager = BossModelI.M_CENTER;
		if (view instanceof ViewReferenceI.AwareI) {
			manager = ((ViewReferenceI.AwareI) view).getManager();
		}
		ManagerModelI mg = this.getManager(manager);
		ViewReferenceI rt = mg.manage(model, view);
		return rt;

	}
}
