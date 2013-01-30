/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import java.util.List;

import com.fs.uicommons.api.gwt.client.manage.ViewReferenceI.AwareI;
import com.fs.uicommons.api.gwt.client.manage.ViewReferenceI;
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

	public List<ViewReferenceI> getMangedList() {
		return this.getChildList(ViewReferenceI.class);
	}

	@Override
	public ViewReferenceI manage(ModelI model, WidgetI w) {
		String key = "managed-" + model.getName();
		ViewReferenceImpl rt = new ViewReferenceImpl(key, w);

		rt.parent(this);

		if (model instanceof ViewReferenceI.AwareI) {
			((ViewReferenceI.AwareI) model).setManaged(rt);
		}

		if (w instanceof ViewReferenceI.AwareI) {
			((ViewReferenceI.AwareI) w).setManaged(rt);
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
	public ViewReferenceI getManaged(String name) {
		ViewReferenceI rt = this.getChild(ViewReferenceI.class, name, true);
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
