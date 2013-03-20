/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 15, 2012
 */
package com.fs.uicommons.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wuzhen
 * @deprecated to be rethink.
 */
public interface AdjusterI extends ElementObjectI {

	public WidgetI getOwner();

	public void addClickHandler(EventHandlerI<ClickEvent> eh);
}
