/**
 *  Dec 27, 2012
 */
package com.fs.uicommons.api.gwt.client.widget;

import com.fs.uicommons.api.gwt.client.widget.event.SelectEvent;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public interface SelectableI {

	public boolean isSelected();

	public void select();

	public void addSelectEventHandler(EventHandlerI<SelectEvent> eh);
}
