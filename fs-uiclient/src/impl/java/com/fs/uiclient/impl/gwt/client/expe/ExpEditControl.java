/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.expe.ExpEditControlI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.impl.gwt.client.handler.action.ExpEditSumbitAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;

/**
 * @author wu
 * 
 */
public class ExpEditControl extends ControlSupport implements ExpEditControlI {

	/**
	 * @param name
	 */
	public ExpEditControl(String name) {
		super(name);
		this.addActionEventHandler(ExpEditModelI.A_SUBMIT, new ExpEditSumbitAP());//

	}

}
