/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.header;

import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public abstract class HeaderItemEHSupport implements EventHandlerI<HeaderItemEvent> {

	protected ModelI getRootModel(HeaderItemEvent t) {
		ModelI rootModel = t.getModel().getClient(true).getRootModel();
		return rootModel;
	}

}
