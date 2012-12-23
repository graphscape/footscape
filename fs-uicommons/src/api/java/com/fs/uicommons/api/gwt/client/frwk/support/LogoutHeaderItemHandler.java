/**
 *  Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.support;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.util.FrwkUtil;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wuzhen
 * 
 */
public class LogoutHeaderItemHandler implements EventHandlerI<ModelValueEvent> {

	public LogoutHeaderItemHandler() {

	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public void handle(ModelValueEvent e) {
		FrwkUtil.getSessionModel(e.getModel()).setLoginRequired(true);//
	}

	public void start(ModelI parent) {

		HeaderModelI.ItemModel logout = FrwkUtil.getFrwkModel(parent)
				.getHeader()
				.addItem(new String[] { "user", "logout" }, ItemModel.P_RIGHT);
		logout.addTriggerHandler(this);
	}
}
