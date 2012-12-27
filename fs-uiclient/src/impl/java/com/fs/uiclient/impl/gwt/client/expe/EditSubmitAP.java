/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.event.ExpCreatedEvent;
import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wuzhen
 * 
 */
public class EditSubmitAP extends APSupport {

	@Override
	protected void processResponseSuccess(ControlI c, String a, UiResponse res) {
		// TODO Auto-generated method stub
		super.processResponseSuccess(c, a, res);
		// refresh list view
		MainModelI fm = c.getModel().getTopObject().find(MainModelI.class, true);
		StringData sd = res.getPayload("expId", true);

		//TODO remove this,by event  
		fm.setValue(MainModelI.L_EXPID_CREATED, sd.getValue());// listen by
																// list control?
		String expId = sd.getValue();
		new ExpCreatedEvent(c, expId).dispatch();//
	}

}
