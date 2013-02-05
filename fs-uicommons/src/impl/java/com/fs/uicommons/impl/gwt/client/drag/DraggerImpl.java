/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.fs.uicommons.impl.gwt.client.drag;

import com.fs.uicommons.api.gwt.client.drag.DragableI;
import com.fs.uicommons.api.gwt.client.drag.DraggerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public class DraggerImpl extends UiObjectSupport implements DraggerI {

	/**
	 * @param c
	 */
	public DraggerImpl(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addDragable(DragableI db) {
		DragableWrapper di = new DragableWrapper(this.container, db);
		di.parent(this);

	}

}
