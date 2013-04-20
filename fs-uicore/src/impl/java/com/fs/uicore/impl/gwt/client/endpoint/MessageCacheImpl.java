/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 28, 2013
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiCoreConstants;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.MessageCacheI;
import com.fs.uicore.api.gwt.client.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.event.StateChangeEvent;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * <p>
 * Cache the request for tracking by response.
 */
public class MessageCacheImpl extends UiObjectSupport implements MessageCacheI {
	private static class Entry {

		public Entry(MessageData md) {
			this.data = md;
			this.created = System.currentTimeMillis();
		}

		private MessageData data;

		private long created;

		public boolean isTimeout(long timeout) {
			return System.currentTimeMillis() > this.created + timeout;
		}

		/*
		 *Apr 4, 2013
		 */
		@Override
		public String toString() {
			return "created:"+created+",data:"+this.data;
		}
		
	}

	private int timeout = UiCoreConstants.TIME_OUT_REQUEST_CACHE;

	private Map<String, Entry> entryMap;

	private ContainerI container;

	private SchedulerI scheduler;

	public MessageCacheImpl(ContainerI c) {
		super(c);
		this.entryMap = new HashMap<String, Entry>();
		this.container = c;
		this.scheduler = c.get(SchedulerI.class, true);
	}

	@Override
	public void stop() {
		// TODO remove task
	}

	@Override
	public void start() {
		this.scheduler.scheduleRepeat("message-cache", this.timeout, new EventHandlerI<ScheduleEvent>() {

			@Override
			public void handle(ScheduleEvent t) {
				MessageCacheImpl.this.checkTimeOut();
			}
		});
	}

	/**
	 * Mar 28, 2013
	 */
	protected void checkTimeOut() {
		Set<String> timeoutIdSet = new HashSet<String>();
		for (Map.Entry<String, Entry> e : this.entryMap.entrySet()) {
			Entry et = e.getValue();
			if (et.isTimeout(this.timeout)) {
				String id = e.getKey();
				timeoutIdSet.add(id);
			}

		}

		for (String id : timeoutIdSet) {
			this.removeMessage(id, true);
		}
	}

	@Override
	public MessageData removeMessage(String id) {
		return this.removeMessage(id, true);
	}

	public MessageData removeMessage(String id, boolean dispatch) {

		Entry en = this.entryMap.remove(id);
		MessageData rt = en == null ? null : en.data;
		if (dispatch) {

			new StateChangeEvent(this).dispatch();
		}
		return rt;
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public void addMessage(MessageData md) {
		this.entryMap.put(md.getId(), new Entry(md));
		new StateChangeEvent(this).dispatch();
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public void addMessage(MsgWrapper mw) {
		this.addMessage(mw.getTarget());
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public MessageData getMessage(String id) {
		//
		Entry en = this.entryMap.get(id);
		if (en == null) {
			return null;
		}
		return en.data;
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public int size() {
		return this.entryMap.size();
	}

}
