/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.state.State;
import com.fs.uicore.api.gwt.client.state.StatefulI;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * @see Window in gwt.
 *      <p>
 *      this is singleton .
 */
public interface WindowI extends UiObjectI, StatefulI {

	public static final State CLOSING = State.valueOf("CLOSING");

	public void resizeTo(Size size);

	public Size getSize();

}
