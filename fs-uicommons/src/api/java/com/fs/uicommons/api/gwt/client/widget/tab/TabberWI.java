/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.tab;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface TabberWI extends CompositeI {

	public static final Location L_SELECTED_TABNAME = Location
			.valueOf("selectedTabName");

	public TabWI getSelected(boolean force);
	
	public TabWI addTab(String name);
	
	public TabWI addTab(String name, WidgetI content);
	
	public TabWI addTab(String name, WidgetI content, boolean sel);

	public TabWI getTab(String name, boolean force);

	public PanelWI getPanel(String name, boolean force);

}
