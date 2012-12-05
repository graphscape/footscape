/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleActionModel;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 * 
 */
public class ControlUtil {

	public static ActionModelI getAction(ModelI model, String action) {

		return getAction(model, action, false);
	}

	public static ActionModelI getAction(ModelI model, String action,
			boolean force) {
		return model.getChild(ActionModelI.class, action, force);
	}

	public static ActionModelI addAction(ModelI model, String name) {
		return addAction(model, name, false);

	}

	public static ActionModelI addAction(ModelI model, String name,
			boolean hidden) {
		ActionModelI old = model.getChild(ActionModelI.class, name, false);
		if (old != null) {
			throw new UiException("cannot add action, duplicated action:"
					+ name + " in model:" + model);
		}
		ActionModelI rt = new SimpleActionModel(name);
		rt.parent(model);//
		rt.setHidden(hidden);
		return rt;
	}

	public static List<ActionModelI> getActionList(ModelI model) {

		return model.getChildList(ActionModelI.class);

	}

	public static void triggerAction(ModelI model, String action) {
		getAction(model, action, true).trigger();
	}

}
