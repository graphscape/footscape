/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.GwtHandlerI;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author wu
 * 
 */
public abstract class GwtHandlerSupport<E extends GwtEvent<H>, H extends EventHandler> 
		implements EventHandler,GwtHandlerI<E,H> {

	protected UiLoggerI LOG = UiLoggerFactory
			.getLogger(GwtHandlerSupport.class);

	protected void handle(E evt) {
		try {
			this.handleInternal(evt);
		} catch (Throwable t) {
			LOG.error("", t);
		}
	}

	public H cast() {
		return (H) this;
	}

	protected abstract void handleInternal(E evt);

}
