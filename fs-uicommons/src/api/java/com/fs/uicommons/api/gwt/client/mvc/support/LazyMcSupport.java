/**
 *  Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wuzhen
 * 
 */
public abstract class LazyMcSupport extends LazyMvcSupport {

	public LazyMcSupport(ModelI parent, String name) {
		super(parent, name);
	}

	@Override
	protected ViewI createView(String name,ContainerI c) {
		return null;
	}

}
