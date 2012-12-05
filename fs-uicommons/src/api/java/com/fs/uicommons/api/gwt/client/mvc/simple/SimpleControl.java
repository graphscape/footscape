/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class SimpleControl extends ControlSupport {

	/**
	 * @param c
	 */
	public SimpleControl(String name) {
		super(name);
	}

	@Override
	public ControlI model(ModelI cm) {
		super.model(cm);

		return this;
	}

}
