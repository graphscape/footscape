/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class BodyControl extends ControlSupport {

	/**
	 * @param name
	 */
	public BodyControl(String name) {
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
		// TODO Auto-generated method stub
		super.model(cm);

		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.mvc.support.AbstractControl#
	 * processChildModelAdd(com.fs.uicore.api.gwt.client.ModelI)
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ViewReferenceI) {
			this.processChildManagedModelAdd((ViewReferenceI) cm);
		}
	}

	/**
	 * @param cm
	 */
	private void processChildManagedModelAdd(ViewReferenceI cm) {

	}

}
