/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.tab;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface TabWI extends WidgetI {

	public static final Location MK_SELECTED = Location.valueOf("SELECTED");

	public String getName();

	public void select();

	public PanelWI getPanel();

	public boolean isSelected();

}
