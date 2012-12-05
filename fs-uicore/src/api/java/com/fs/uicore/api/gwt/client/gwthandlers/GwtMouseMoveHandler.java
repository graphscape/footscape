/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.fs.uicore.api.gwt.client.gwthandlers;

import com.fs.uicore.api.gwt.client.support.GwtHandlerSupport;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseMoveHandler extends
		GwtHandlerSupport<MouseMoveEvent, MouseMoveHandler> implements
		MouseMoveHandler {
	@Override
	public void onMouseMove(MouseMoveEvent evt) {
		super.handle(evt);
	}
}
