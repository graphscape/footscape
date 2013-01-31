/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public class EndpointCloseHandler extends UiHandlerSupport implements EventHandlerI<EndpointBondEvent> {

	/**
	 * @param c
	 */
	public EndpointCloseHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(EndpointBondEvent e) {

	}
}
