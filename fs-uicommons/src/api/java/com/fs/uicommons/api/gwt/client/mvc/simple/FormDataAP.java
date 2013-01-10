/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public abstract class FormDataAP extends ActionHandlerSupport {

	private String form;

	public FormDataAP() {
		this(null);
	}

	public FormDataAP(String form) {
		this.form = form;
	}

	protected void processFormData(ModelI md, MsgWrapper req) {
		ObjectPropertiesData dt = this.getFormData(md);
		req.setPayloads(dt);
	}

	protected ObjectPropertiesData getFormData(ModelI fsm) {

		if (!(fsm instanceof FormsModel)) {
			throw new UiException("the model:" + fsm+ " not a FormsModel");
		}

		FormsModel fcm = (FormsModel) fsm;
		FormModel fm = null;
		if (this.form == null) {
			fm = fcm.getDefaultForm();
		} else {
			fm = fcm.getForm(form, true);
		}
		return fm.getData();

	}

}
