/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.echo;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.simple.FormDataAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class EchoControl extends ControlSupport {

	/**
	 * @param name
	 */
	public EchoControl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.support.AbstractControl#model(com
	 * .fs.uicommons.api.gwt.client.mvc.ControlModelI)
	 */
	@Override
	public ControlI model(ModelI cm) {
		super.model(cm);
		this.addActionProcessor("echo", new FormDataAP());
		return this;
	}

}
