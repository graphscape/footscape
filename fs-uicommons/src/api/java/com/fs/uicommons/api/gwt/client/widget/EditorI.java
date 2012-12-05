/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.widget;

import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public interface EditorI<T extends UiData> extends WidgetI {

	public T getData();

	public void input(T d);
	/*
	 * public void addListener(Event.Type<EditEvent<D>> type,
	 * ListenerI<EditEvent<D>> l);
	 */

}
