/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 16, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class FormInitAP extends APSupport {

	private String form;

	private String payload;// key in payload

	public FormInitAP(String form, String payload) {
		this.form = form;
		this.payload = payload;
	}

	@Override
	protected void processResponseSuccess(final ControlI c, final String a,
			UiResponse res) {
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
		ObjectPropertiesData opd = (ObjectPropertiesData) res.getPayloads()
				.getProperty(this.payload, false);
		
		fm.setDefaultValue(opd);//
	}
}
