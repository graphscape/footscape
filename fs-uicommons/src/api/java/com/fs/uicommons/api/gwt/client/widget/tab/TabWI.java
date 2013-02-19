/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.tab;

import com.fs.uicommons.api.gwt.client.widget.SelectableI;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface TabWI extends WidgetI, SelectableI {

	public String getName();
	
	public void setText(String text);

	public PanelWI getPanel();
	
	public WidgetI getManaged();

	public void close();

}
