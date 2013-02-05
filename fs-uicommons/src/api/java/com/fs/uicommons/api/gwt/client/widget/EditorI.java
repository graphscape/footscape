/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.widget;

import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public interface EditorI<T> extends WidgetI {

	public T getData();
	
	public void setData(T d);

	public void input(T d);

}
