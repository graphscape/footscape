/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.xmpp.modules.ResourceBinderModule;
import tigase.jaxmpp.core.client.xmpp.modules.ResourceBinderModule.ResourceBindEvent;

import com.fs.uixmpp.core.api.gwt.client.event.ResourceBindSuccessEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;

/**
 * @author wu
 * 
 */
public class ResourceBindSuccessEC extends
		EventConverter<ResourceBindEvent, ResourceBindSuccessEvent> {

	/**
	 * @param et
	 */
	public ResourceBindSuccessEC() {
		super(ResourceBinderModule.ResourceBindSuccess, "ResourceBindSuccess");
	}

	@Override
	public ResourceBindSuccessEvent convert(Context<ResourceBindEvent> e) {

		ResourceBindSuccessEvent rt = new ResourceBindSuccessEvent(e.getXmpp());
		return rt;
	}

}
