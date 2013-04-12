/**
 *  Jan 31, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class OpenResourceItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	private Path htmlPath;

	/**
	 * @param c
	 */
	public OpenResourceItemHandler(ContainerI c, Path path) {
		super(c);
		this.htmlPath = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(HeaderItemEvent t) {

		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.openHtmlResource(this.htmlPath, true);

	}

}
