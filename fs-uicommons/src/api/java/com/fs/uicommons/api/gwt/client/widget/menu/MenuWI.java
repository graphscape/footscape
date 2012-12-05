/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.menu;

import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface MenuWI extends CompositeI {

	public MenuItemWI addItem(String name);

	public MenuItemWI addItem(String name, MenuWI subm);

	public MenuItemWI getItem(String name);

	public void openBy(WidgetI src);
	
	public void close();

}
