/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api.data;

import java.util.UUID;

import com.fs.commons.api.message.MessageI;
import com.fs.datagrid.api.gdata.MessageGd;
import com.fs.expector.api.EventType;
import com.fs.expector.api.GridedDataI;

/**
 * @author wu
 * 
 */
public class EventGd extends MessageGd implements GridedDataI {

	public static final String HK_TYPE = "_type";

	public static final String HK_ID = "_id";

	public static final String HK_ORIGIN_EVENT_ID = "_originEventId";

	public static final String HK_CAUSE_EVENT_ID = "_causeEventId";

	public EventGd() {

	}

	public EventGd(MessageI msg) {
		super(msg);
	}

	public EventGd(EventType type) {
		this(type, UUID.randomUUID().toString());//
	}

	public EventGd(EventType type, String id) {
		this.setHeader(HK_TYPE, type.name());
		this.setHeader(HK_ID, id);
	}

	public EventType getType() {
		String ts = this.getHeader(HK_TYPE, true);
		return EventType.valueOf(ts);
	}

	public String getId() {
		return this.getHeader(HK_ID, true);
	}

}
