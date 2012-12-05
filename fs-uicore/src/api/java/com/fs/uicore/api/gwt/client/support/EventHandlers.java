/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.FilterI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 *         <p>
 *         each UiObjectI has one this instance as helper. for derfered attach
 *         to the event bus.
 */
public class EventHandlers {
	public static class Entry {
		private FilterI filter;
		private HandlerI handler;
		private boolean attached;

		Entry(FilterI f, HandlerI h, boolean attached) {
			this.filter = f;
			this.handler = h;
			this.attached = attached;
		}
	}

	protected UiObjectI owner;

	protected List<Entry> entryList = new ArrayList<Entry>();

	public EventHandlers(UiObjectI owner) {
		this.owner = owner;
	}

	public <E extends Event> void addHandler(FilterI ef, HandlerI<E> eh) {
		boolean attached = this.owner.isAttached();
		Entry e = new Entry(ef, eh, attached);
		this.entryList.add(e);
		if (attached) {
			this.attach(e);
		}
	}

	protected void attach(Entry e) {
		this.owner.getEventBus(true).addHandler(e.filter, e.handler);
		e.attached = true;
	}

	/**
	 * 
	 */
	public void onOwnerAttach() {
		for (Entry e : this.entryList) {
			if (e.attached) {
				continue;
			}
			this.attach(e);
		}
	}

	public void onOwnerDettach() {
		// TODO
	}
}
