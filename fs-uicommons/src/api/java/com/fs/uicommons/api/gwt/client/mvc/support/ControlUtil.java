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
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ControlUtil {

	public static ActionModelI getAction(ModelI model, Path path) {

		return getAction(model, path, false);
	}

	public static ActionModelI getAction(ModelI model, Path path, boolean force) {
		String action = path.getName();//
		return model.getChild(ActionModelI.class, action, force);
		
	}

	public static ActionModelI addAction(ModelI model, Path path) {
		return addAction(model, path, false);

	}

	public static ActionModelI addAction(ModelI model, Path path, boolean hidden) {
		String name = path.getName();
		ActionModelI old = model.getChild(ActionModelI.class, name, false);
		if (old != null) {
			throw new UiException("cannot add action, duplicated action:" + name + " in model:" + model);
		}
		ActionModelI rt = new SimpleActionModel(path);
		rt.parent(model);//
		rt.setHidden(hidden);
		return rt;
	}

	public static List<ActionModelI> getActionList(ModelI model) {

		return model.getChildList(ActionModelI.class);

	}

}
