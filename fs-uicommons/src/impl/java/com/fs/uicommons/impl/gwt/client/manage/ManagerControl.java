/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.ModelValueHandler;

/**
 * @author wu
 * 
 */
public class ManagerControl extends ControlSupport {

	/**
	 * @param name
	 */
	public ManagerControl(String name) {
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
		if (cm instanceof ManagedModelI) {
			this.processChildManagedModelAdd((ManagedModelI) cm);
		}
	}

	/**
	 * @param cm
	 */
	private void processChildManagedModelAdd(ManagedModelI cm) {
		cm.addValueHandler(ModelI.L_DEFAULT, new ModelValueHandler<Boolean>() {

			@Override
			public void handleValue(Boolean value) {

			}
		});
	}

}
