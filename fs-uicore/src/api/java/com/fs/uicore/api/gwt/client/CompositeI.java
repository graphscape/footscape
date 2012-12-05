/**
 * Jun 30, 2012
 */
package com.fs.uicore.api.gwt.client;

import java.util.List;

import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface CompositeI extends WidgetI {

	public CompositeI child(WidgetI w);

	public List<WidgetI> getChildWidgetList();

}
