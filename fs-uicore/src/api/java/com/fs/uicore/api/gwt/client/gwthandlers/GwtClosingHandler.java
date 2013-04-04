/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.fs.uicore.api.gwt.client.gwthandlers;

import com.fs.uicore.api.gwt.client.support.GwtHandlerSupport;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtClosingHandler extends GwtHandlerSupport<ClosingEvent, ClosingHandler> implements
		ClosingHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onWindowClosing(ClosingEvent event) {
		this.handle(event);// o
	}

}
