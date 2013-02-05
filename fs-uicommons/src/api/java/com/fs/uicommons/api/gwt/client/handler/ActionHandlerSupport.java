/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public abstract class ActionHandlerSupport extends UiHandlerSupport implements EventHandlerI<ActionEvent> {

	public ActionHandlerSupport(ContainerI c) {
		super(c);
	}

	protected <T extends ViewI> T findView(Class<T> vcls, boolean force) {
		return this.getClient(true).getRoot().find(vcls, force);
	}
}
