/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.event.BodyItemCreatedEvent;
import com.fs.uicommons.api.gwt.client.event.BodyItemSelectEvent;
import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class BodyModelImpl extends ModelSupport implements BodyModelI {

	protected Map<Path, ViewReferenceI> viewReferenceMap;

	/**
	 * @param name
	 */
	public BodyModelImpl(String name) {
		super(name);
		this.viewReferenceMap = new HashMap<Path, ViewReferenceI>();
	}

	@Override
	public ViewReferenceI manage(ModelI model, WidgetI w) {
		String key = "managed-" + model.getName();
		Path p = Path.valueOf(key);
		ViewReferenceImpl rt = new ViewReferenceImpl(p, w, this);
		this.viewReferenceMap.put(p, rt);// TODO by a list?
		if (model instanceof ViewReferenceI.AwareI) {
			((ViewReferenceI.AwareI) model).setViewReference(rt);
		}
		if (w instanceof ViewReferenceI.AwareI) {
			((ViewReferenceI.AwareI) w).setViewReference(rt);
		}
		new BodyItemCreatedEvent(this, p).dispatch();
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
	public ViewReferenceI getManaged(Path name) {
		ViewReferenceI rt = this.viewReferenceMap.get(name);
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
	public void showManaged(Path managed) {
		this.getManaged(managed).select(true);
	}

	public void selectView(ViewReferenceI vr, boolean sel) {
		new BodyItemSelectEvent(this, vr.getPath(), sel).dispatch();
	}

}
