/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wu
 * 
 */
public class BondHandler implements EventHandlerI<EndpointBondEvent> {
	MainControlI control;

	public BondHandler(MainControlI c) {
		this.control = c;
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointBondEvent t) {
		ModelI rootM = t.getSource().getClient(true).getRootModel();//
		UserInfo ui = t.getChannel().getUserInfo();

		this.control.getLazyObject(MainControlI.LZ_EXP_SEARCH, true);//
		if (!ui.isAnonymous()) {//TODO acc change

			// command/data from server side.
			// after auth,active activities control.
			this.control.getLazyObject(MainControlI.LZ_ACTIVITIES, true);
			// active search
			// active user exp list
			this.control.getLazyObject(MainControlI.LZ_UE_LIST, true);
			// active cooper control.
			this.control.getLazyObject(MainControlI.LZ_COOPER, true);

			this.control.getLazyObject(MainControlI.LZ_ACHAT, true);
		}
	}

}
