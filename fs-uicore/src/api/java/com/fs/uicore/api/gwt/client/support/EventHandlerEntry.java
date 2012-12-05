/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 1, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public class EventHandlerEntry {

	private Event.FilterI filter;
	private Event.HandlerI handler;
	private static final UiLoggerI LOG = UiLoggerFactory
			.getLogger(EventHandlerEntry.class);

	public EventHandlerEntry(Event.FilterI f, Event.HandlerI<?> h) {
		this.filter = f;
		this.handler = h;
	}

	/**
	 * @return the filter
	 */
	public Event.FilterI getFilter() {
		return filter;
	}

	/**
	 * @return the handler
	 */
	public Event.HandlerI<?> getHandler() {
		return handler;
	}

	public void tryHandle(Event evt) {

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("tryHandle event:" + evt + " by filter:"
						+ this.filter + " and handler:" + this.handler);
			}

			Event ef = this.filter.filter(evt);

			if (ef == null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("event is filted by:" + this.filter);
				}
				return;
			}

			this.handler.handle(ef);

		} catch (RuntimeException e) {
			throw new UiException("handler exception,event:" + evt
					+ ",handler:" + this.handler + ",filter:" + filter, e);
		}
	}

}
