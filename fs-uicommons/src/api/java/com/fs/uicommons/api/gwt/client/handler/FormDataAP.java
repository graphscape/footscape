/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public abstract class FormDataAP extends ActionHandlerSupport {

	private String form;

	public FormDataAP(ContainerI c) {
		this(c, null);
	}

	public FormDataAP(ContainerI c, String form) {
		super(c);
		this.form = form;
	}

	protected void processFormData(ActionEvent ae, MsgWrapper req) {
		FormsModel md = (FormsModel) ae.getProperty(Constants.AK_FORMS_MODEL);
		ObjectPropertiesData dt = this.getFormData(md);
		req.setPayloads(dt);
	}

	protected ObjectPropertiesData getFormData(FormsModel fsm) {

		if (!(fsm instanceof FormsModel)) {
			throw new UiException("the model:" + fsm + " not a FormsModel");
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
