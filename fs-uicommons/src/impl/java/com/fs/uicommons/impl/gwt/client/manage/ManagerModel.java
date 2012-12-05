/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import java.util.List;

import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagerModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ManagerModel extends ModelSupport implements ManagerModelI {

	/**
	 * @param name
	 */
	public ManagerModel(String name) {
		super(name);
	}

	public List<ManagedModelI> getMangedList() {
		return this.getChildList(ManagedModelI.class);
	}

	@Override
	public ManagedModelI manage(ModelI model, WidgetI w) {
		String key = "managed-" + model.getName();
		ManagedModelImpl rt = new ManagedModelImpl(key, w);

		rt.parent(this);

		if (model instanceof ManagableI) {
			((ManagableI) model).setManaged(rt);
		}

		if (w instanceof ManagableI) {
			((ManagableI) w).setManaged(rt);
		}

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI#getManaged(java.lang
	 * .String)
	 */
	@Override
	public ManagedModelI getManaged(String name) {
		ManagedModelI rt = this.getChild(ManagedModelI.class, name, true);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI#showManaged(java.lang
	 * .String)
	 */
	@Override
	public void showManaged(String managed) {
		this.getManaged(managed).select(true);
	}

}
