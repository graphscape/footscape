/**
 * Jun 29, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.BasicI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.state.State;

/**
 * @author wu
 * 
 */
public interface ButtonI extends BasicI {

	// todo move to upper
	public static final Location L_STATE = Location.valueOf("_state");

	public static State DOWN = State.valueOf("down");
	
	public static State UP = State.valueOf("up");
	
	public State getState();
	
	public void setText(String text);
	
}
