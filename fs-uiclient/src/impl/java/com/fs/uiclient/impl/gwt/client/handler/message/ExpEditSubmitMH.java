/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.event.ExpCreatedEvent;
import com.fs.uiclient.api.gwt.client.expe.ExpEditControlI;
import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpEditSubmitMH extends MHSupport {

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		ControlI c = this.getControl(t, ExpEditControlI.class, true);
		MessageData res = t.getMessage();
		MainModelI fm = c.getModel().getTopObject().find(MainModelI.class, true);
		String sd = res.getString("expId", true);

		// TODO remove this,by event
		fm.setValue(MainModelI.L_EXPID_CREATED, sd);// listen by
													// list control?
		new ExpCreatedEvent(c, sd).dispatch();//
	}
}
