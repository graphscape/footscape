/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.EventDispatcherI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.FilterI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author wu
 * 
 */
public class DefaultEventDispatcher implements EventDispatcherI {

	protected List<EventHandlerEntry> handlerList = new ArrayList<EventHandlerEntry>();

	protected UiObjectI owner;

	protected boolean isEventBus;

	protected UiLoggerI log;

	public DefaultEventDispatcher(UiObjectI source, UiLoggerI log) {
		this.owner = source;
		this.isEventBus = (this.owner instanceof EventBusI);
		this.log = log;
	}

	@Override
	public <E extends Event> void addHandler(UiObjectI src, HandlerI<E> l) {
		this.addHandler(src, null, l);
	}

	/*
	 * if ec is null,the listener will interesting any event.
	 */
	@Override
	public <E extends Event> void addHandler(UiObjectI src, Type<E> ec,
			HandlerI<E> eh) {
		FilterI ef = SimpleEventFilter.valueOf(ec, src);
		this.addHandler(ef, eh);
	}

	@Override
	public <E extends Event> void addHandler(FilterI ef, HandlerI<E> eh) {
		this.handlerList.add(new EventHandlerEntry(ef, eh));
	}

	public List<EventHandlerEntry> getCopiedEHEList() {
		return new ArrayList<EventHandlerEntry>(this.handlerList);
	}

	@Override
	public void dispatch(Event evt) {
		if (log.isDebugEnabled()) {
			log.debug("dispatch event:" + evt);
		}
		try {
			this.dispatchLocal(evt);// local handlers

			if (!evt.isGlobal()) {
				return;//
			}

			// global event notify to event bus.
			// to event bus handlers.
			if (!this.isEventBus) {// if is not bus,then get bus and notify it.
				EventBusI eb = this.owner.getEventBus(false);
				if (eb != null) {
					eb.dispatch(evt);//
				}
			}
		} finally {
			//
		}
	}

	public void dispatchLocal(final Event evt) {

		// dispatch global event
		List<EventHandlerEntry> hL = this.getCopiedEHEList();
		if (log.isDebugEnabled()) {
			log.debug("total handlers:" + hL.size());
		}
		for (final EventHandlerEntry he : hL) {

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					//
					try {
						he.tryHandle(evt);
					} catch (Throwable t) {
						log.error("event handle error ", t);

					}
				}
			});

		}
	}

}
