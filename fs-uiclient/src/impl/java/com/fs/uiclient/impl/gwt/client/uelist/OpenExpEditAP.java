/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu create a new exp
 */
public class OpenExpEditAP implements ActionProcessorI {

	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		MainControlI mc = c.getManager().getControl(MainControlI.class, true);
		
		// TODO create only when required.

		Mvc mvc = mc.getLazyObject(MainControlI.LZ_EXP_EDIT, true);
		
		ExpEditModelI eem = mvc.getModel();

		eem.setValue(ExpEditModelI.L_ISOPEN, true);// listen by the view of the
													// model.
	}

	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {

	}

}
