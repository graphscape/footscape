/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.api.data;

import java.util.UUID;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.GridedDataI;
import com.fs.gridservice.core.api.gdata.MessageGd;

/**
 * @author wu
 * 
 */
public class EventGd extends MessageGd implements GridedDataI {

	public static final String HK_TYPE = "_type";

	public static final String HK_ID = MessageI.HK_ID;

	public static final String HK_ORIGIN_EVENT_ID = "_originEventId";

	public static final String HK_CAUSE_EVENT_ID = "_causeEventId";

	public static final String HK_SOURCE_TERMINAL_ID = "_source_terminal_id";

	public EventGd() {

	}

	public EventGd(MessageI msg) {
		super(msg);
	}

	public EventGd(EventType type, Path path) {
		this(type, path, UUID.randomUUID().toString());//
	}

	public EventGd(EventType type, Path path, String id) {
		this.setHeader(HK_TYPE, type.name());
		this.setHeader(HK_PATH, path.toString());
		this.setHeader(HK_ID, id);
	}

	public EventType getType() {
		String ts = this.getHeader(HK_TYPE, true);
		return EventType.valueOf(ts);
	}

	public String getSourceTerminalId() {
		return this.getHeader(HK_SOURCE_TERMINAL_ID);
	}

	public String getId() {
		return this.getHeader(HK_ID, true);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
