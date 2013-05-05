/**
 *  Jan 31, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class ContactUsBottomItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public ContactUsBottomItemHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(HeaderItemEvent t) {

		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.openContactUsView(true);
	}

}
