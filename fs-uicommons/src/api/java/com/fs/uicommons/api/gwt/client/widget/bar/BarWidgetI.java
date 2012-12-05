/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 6, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.bar;

import com.fs.uicommons.api.gwt.client.Position;
import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface BarWidgetI extends CompositeI {
	

	public static final Position P_LEFT = Position.valueOf("left");

	public static final Position P_RIGHT = Position.valueOf("right");

	public static final Position P_CENTER = Position.valueOf("center");


	public void addItem(Position pos, WidgetI cw);
	
}
