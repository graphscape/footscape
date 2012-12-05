/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class FormDataAP extends APSupport {

	private String form;

	public FormDataAP() {
		this(null);
	}

	public FormDataAP(String form) {
		this.form = form;
	}

	/*
	 */
	@Override
	public void processRequest(final ControlI c, final String a, UiRequest req) {

		ModelI cm = c.getModel();
		if (!(cm instanceof FormsModel)) {
			throw new UiException("the model of control:" + c
					+ " not a FormModel");
		}
		FormsModel fcm = (FormsModel) cm;
		FormModel fm = null;
		if (this.form == null) {
			fm = fcm.getDefaultForm();
		} else {
			fm = fcm.getForm(form, true);
		}
		req.setPayloads(fm.getData());

	}

	@Override
	public void processResponse(final ControlI c, final String a, UiResponse res) {

	}
}
