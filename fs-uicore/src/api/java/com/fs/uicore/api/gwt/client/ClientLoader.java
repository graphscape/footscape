/**
 *  
 */
package com.fs.uicore.api.gwt.client;

/**
 * @author wu
 *
 */
/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.google.gwt.core.shared.GWT;

/**
 * @author wu Test support.
 */
public abstract class ClientLoader {
	
	private static ClientLoader ME = GWT.create(ClientLoader.class);
	
	public static ClientLoader getInstance(){
		return ME;
	}

	public abstract GwtSPI.Factory getOrLoadClient(GwtSPI[] spis, EventHandlerI<Event> l);

	public abstract GwtSPI.Factory loadClient(GwtSPI[] spis, EventHandlerI<Event> l);

}
