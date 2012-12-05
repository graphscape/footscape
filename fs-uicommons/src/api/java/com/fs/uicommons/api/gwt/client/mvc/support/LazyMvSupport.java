/**
 *  Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wuzhen
 * 
 */
public abstract class LazyMvSupport extends LazyMvcSupport {

	public LazyMvSupport(ModelI parent, String name) {
		super(parent, name);
	}

	@Override
	protected ControlI createControl(String name) {
		return null;
	}

}
