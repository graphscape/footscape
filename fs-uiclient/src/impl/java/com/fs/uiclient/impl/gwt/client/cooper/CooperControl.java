/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import com.fs.uiclient.api.gwt.client.coper.CoperControlI;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;

/**
 * @author wu
 * 
 */
public class CooperControl extends ControlSupport implements CoperControlI {

	/**
	 * @param name
	 */
	public CooperControl(String name) {
		super(name);
		this.addActionProcessor(CooperModelI.A_REQUEST, new CooperRequestAP());
		this.addActionProcessor(CooperModelI.A_CONFIRM, new CooperConfirmAP());

	}

}
