/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
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

		FormsViewI fv = (FormsViewI) ae.getProperty(Constants.AK_FORMS_VIEW);
		ObjectPropertiesData dt = (ObjectPropertiesData) this.getFormData(fv);

		req.setPayloads(dt);
	}

	protected ObjectPropertiesData getFormData(FormsViewI sv) {
		if (this.form == null) {
			return sv.getDefaultForm().getData();
		} else {
			return sv.getForm(this.form).getData();
		}
	}

}
