/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.api.gwt.client.support;

import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public abstract class MHSupport extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public MHSupport(ContainerI c) {
		super(c);
	}

}
