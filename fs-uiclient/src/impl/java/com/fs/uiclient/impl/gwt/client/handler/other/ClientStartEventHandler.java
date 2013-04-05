/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 13, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.impl.gwt.client.HeaderNames;
import com.fs.uiclient.impl.gwt.client.UiClientConstants;
import com.fs.uicommons.api.gwt.client.frwk.BottomViewI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;

/**
 * @author wu
 * 
 */
public class ClientStartEventHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {

	/**
	 * @param c
	 */
	public ClientStartEventHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void handle(AfterClientStartEvent t) {
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		//
		fc.addHeaderItem(HeaderNames.H1_LOGO, true);//left
		// right
		fc.addHeaderItemIfNotExist(HeaderNames.H1_SEARCH);
		fc.addHeaderItemIfNotExist(HeaderNames.H1_MYEXP);//anonymous will notify:no right to show exp
		fc.addHeaderItem(HeaderNames.H2_SIGNUP);
		//
		BottomViewI bv = fc.getBottomView();
		bv.addItem(UiClientConstants.BI_ABOUT);
		bv.addItem(UiClientConstants.BI_COTACT);
		
	}

}
